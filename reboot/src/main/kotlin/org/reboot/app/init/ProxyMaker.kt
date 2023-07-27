package org.reboot.app.init

import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import org.reboot.app.Bean
import org.reboot.app.annotation.aspect.Aspect

object ProxyMaker {
    fun make(clazz: Class<*>, classes: List<Class<*>>): Bean {
        val constructor = ComponentInitializer.findConstructor(clazz, classes)
        val targetClass = constructor.declaringClass
        val parameterTypes = constructor.parameterTypes
        return Enhancer().run {
            setSuperclass(targetClass)
            setCallback(createMethodInterceptor(targetClass))
            Bean(
                targetClass,
                when {
                    parameterTypes.isNotEmpty() -> create(
                        parameterTypes, parameterTypes.map {
                            ComponentInitializer.initClass(it, classes)
                        }.toTypedArray<Any>()
                    )

                    else -> create()
                }
            )
        }
    }

    private fun createMethodInterceptor(targetClass: Class<out Any>) =
        MethodInterceptor { obj, method, args, proxy ->
            val actualAspects = ComponentInitializer.aspects.filter {
                val aspect = it.javaClass.getAnnotation(Aspect::class.java)
                targetClass.isAnnotationPresent(aspect.annotation.java)
            }.sortedBy { it.javaClass.getAnnotation(Aspect::class.java).order }
            actualAspects.forEach { it.before(obj, method, args, proxy) }
            when {
                proxy == null -> null
                method == null -> null
                else -> proxy.invokeSuper(obj, args)
            }.also {
                actualAspects.reversed().forEach { it.after(obj, method, args, proxy) }
            }
        }
}
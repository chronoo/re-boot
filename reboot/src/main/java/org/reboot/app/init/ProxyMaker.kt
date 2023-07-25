package org.reboot.app.init

import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import org.reboot.app.Bean
import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Aspect
import java.lang.reflect.Method

object ProxyMaker {
    fun make(clazz: Class<*>, classes: List<Class<*>>): Any {
        val constructor = ComponentInitializer.findConstructor(clazz, classes)
        val enhancer = Enhancer()
        enhancer.setSuperclass(constructor.declaringClass)
        enhancer.setCallback(object : MethodInterceptor {
            override fun intercept(
                obj: Any?,
                method: Method?,
                args: Array<out Any>?,
                proxy: MethodProxy?
            ): Any? {
                val actualAspects = ComponentInitializer.aspects.filter {
                    val aspect = it.javaClass.getAnnotation(Aspect::class.java)
                    constructor.declaringClass.isAnnotationPresent(aspect.annotation.java)
                }.sortedBy { it.javaClass.getAnnotation(Aspect::class.java).order }
                actualAspects.forEach { it.before(obj, method, args, proxy) }
                return when {
                    proxy == null -> null
                    method == null -> null
                    else -> proxy.invokeSuper(obj, args)
                }.also { actualAspects.reversed().forEach { it.after(obj, method, args, proxy) } }
            }
        })
        val parameterTypes = constructor.parameterTypes
        return if (parameterTypes.isNotEmpty()) {
            enhancer.create(
                parameterTypes, parameterTypes.map { ComponentInitializer.initClass(it, classes) }.toTypedArray<Any>()
            )
        } else {
            enhancer.create()
        }.apply {
            ReBootContext.contextMap[clazz] = Bean(
                constructor.declaringClass, this
            )
        }
    }
}
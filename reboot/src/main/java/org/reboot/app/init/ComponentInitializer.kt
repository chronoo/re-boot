package org.reboot.app.init

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component

object ComponentInitializer : ClassInitializer {
    override fun init(classes: List<Class<*>>) {
        classes.filter { it.isAnnotationPresent(Component::class.java) }
            .forEach { initClass(it, classes) }
    }

    private fun initClass(clazz: Class<*>, classes: List<Class<*>>): Any =
        when (val instance = ReBootContext.getByClass(clazz)) {
            null -> {
                val constructor = findConstructor(clazz, classes)
                val parameterTypes = constructor.parameterTypes
                constructor.newInstance(
                    *parameterTypes.map { initClass(it, classes) }.toTypedArray<Any>()
                ).apply {
                    ReBootContext.contextMap[clazz] = this as Any
                }
            }

            else -> instance
        }

    private fun findConstructor(clazz: Class<*>, classes: List<Class<*>>) =
        clazz.declaredConstructors.firstOrNull()
            ?: classes.firstOrNull {
                clazz.isAssignableFrom(it) && it.declaredConstructors.isNotEmpty()
            }?.declaredConstructors?.firstOrNull()
            ?: throw IllegalAccessException()
}

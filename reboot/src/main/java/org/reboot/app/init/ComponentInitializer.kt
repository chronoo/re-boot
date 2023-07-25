package org.reboot.app.init

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component
import org.reboot.app.aspect.AspectProcessor
import org.reboot.app.aspect.LoggingAspect

object ComponentInitializer : ClassInitializer {
    val aspects: List<AspectProcessor> = listOf(
        LoggingAspect
    )

    override fun init(classes: List<Class<*>>) {
        classes.filter { it.isAnnotationPresent(Component::class.java) }
            .forEach { initClass(it, classes) }
    }

    fun initClass(clazz: Class<*>, classes: List<Class<*>>): Any =
        when (val instance = ReBootContext.getByClass(clazz)) {
            null -> ProxyMaker.make(clazz, classes)
            else -> instance
        }

    fun findConstructor(clazz: Class<*>, classes: List<Class<*>>) =
        clazz.declaredConstructors.firstOrNull()
            ?: classes.firstOrNull {
                clazz.isAssignableFrom(it) && it.declaredConstructors.isNotEmpty()
            }?.declaredConstructors?.firstOrNull()
            ?: throw IllegalAccessException()
}

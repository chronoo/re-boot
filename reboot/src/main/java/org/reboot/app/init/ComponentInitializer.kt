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
                val constructor = clazz.declaredConstructors.firstOrNull() ?: classes.firstOrNull {
                    clazz.isAssignableFrom(
                        it
                    ) && it.declaredConstructors.isNotEmpty()
                }
                    ?.declaredConstructors?.firstOrNull() ?: throw IllegalAccessException()
                val parameterTypes = constructor.parameterTypes
                val component =
                    constructor.newInstance(*parameterTypes.map { initClass(it, classes) }.toTypedArray()).apply {
                        ReBootContext.contextMap[clazz] = this as Any
                    }
                component
            }

            else -> instance
        }
}

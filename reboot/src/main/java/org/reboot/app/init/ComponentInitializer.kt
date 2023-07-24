package org.reboot.app.init

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component
import java.lang.reflect.Constructor

object ComponentInitializer: ClassInitializer {
    override fun init(classes: List<Class<*>>) {
        classes.filter { it.isAnnotationPresent(Component::class.java) }
            .forEach { initClass(it) }
    }

    private fun<T> initClass(clazz: Class<T>): T =
        when (val instance = ReBootContext.contextMap[clazz] as T?) {
            null -> {
                val constructor: Constructor<T> = clazz.declaredConstructors.first() as Constructor<T>
                val parameterTypes = constructor.parameterTypes
                val component = constructor.newInstance(*parameterTypes.map { initClass(it) }.toTypedArray()).apply {
                    ReBootContext.contextMap[clazz] = this as Any
                }
                component
            }

            else -> instance
        }
}

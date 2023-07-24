package org.reboot.app.processor

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Config
import org.reboot.app.internal.fillProps

object ConfigInitializer {
    fun init(classes: List<Class<*>>) {
        val properties = PropertyReader.readProperties()
        val configs = classes.filter { it.isAnnotationPresent(Config::class.java) }
        ReBootContext.contextMap.putAll(
            configs.associateWith { it.getDeclaredConstructor().newInstance()
                .apply { fillProps(properties, it.getAnnotation(Config::class.java)) } }
        )

    }
}

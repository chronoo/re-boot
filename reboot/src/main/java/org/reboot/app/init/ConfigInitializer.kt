package org.reboot.app.init

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Config
import org.reboot.app.internal.fillProps
import org.reboot.app.processor.PropertyReader

object ConfigInitializer : ClassInitializer {
    override fun init(classes: List<Class<*>>) {
        val properties = PropertyReader.readProperties()
        val configs = classes.filter { it.isAnnotationPresent(Config::class.java) }
        ReBootContext.contextMap.putAll(
            configs.associateWith { it.getDeclaredConstructor().newInstance()
                .apply { fillProps(properties, it.getAnnotation(Config::class.java)) }
            }
        )
    }
}

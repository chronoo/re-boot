package org.reboot.app.init

interface ClassInitializer {
    fun init(classes: List<Class<*>>)
}
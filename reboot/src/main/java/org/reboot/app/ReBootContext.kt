package org.reboot.app

import com.google.common.reflect.ClassPath
import org.reboot.app.init.ComponentInitializer
import org.reboot.app.init.ConfigInitializer
import org.reboot.app.processor.Processor

object ReBootContext {
    val contextMap: MutableMap<Class<*>, Any> = mutableMapOf()

    fun run() {
        println("init context...")
        initBeans()
        println(contextMap)
        invokeProcessors()
        println("context was initialized")
    }

    fun getByClass(clazz: Class<*>): Any? {
        val targetClass = contextMap.keys.filterIsInstance(clazz)
            .firstOrNull() ?: return null
        return contextMap[targetClass]
    }

    private fun invokeProcessors() {
        contextMap.filterValues { it is Processor }
            .forEach { (_, bean) -> (bean as Processor).process() }
    }

    private fun initBeans() {
        val classes = getAllClasses()
        ConfigInitializer.init(classes)
        ComponentInitializer.init(classes)
    }

    private fun getAllClasses(): List<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader
        val classes = ClassPath.from(classLoader).topLevelClasses
            .mapNotNull {
                runCatching { it.load() }.getOrNull()
            }
        return classes
    }
}
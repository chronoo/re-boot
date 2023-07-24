package org.reboot.app

import com.google.common.reflect.ClassPath
import org.reboot.app.annotation.Component
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

    private fun invokeProcessors() {
        contextMap.filterValues { it is Processor }
            .forEach { (_, bean) -> (bean as Processor).process() }
    }

    private fun initBeans() {
        val classLoader = Thread.currentThread().contextClassLoader
        val classes = ClassPath.from(classLoader).topLevelClasses
            .mapNotNull {
                runCatching { it.load() }.getOrNull()
            }

        classes.filter { it.isAnnotationPresent(Component::class.java) }
            .forEach { init(it) }
    }

    private fun init(clazz: Class<*>): Any =
        when (val instance = contextMap[clazz]) {
            null -> {
                val constructor = clazz.declaredConstructors.first()
                val parameterTypes = constructor.parameterTypes
                constructor.newInstance(*parameterTypes.map { init(it) }.toTypedArray()).apply {
                    contextMap[clazz] = this
                }
            }
            else -> instance
        }
}
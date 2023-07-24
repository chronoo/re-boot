package org.reboot.app

import com.google.common.reflect.ClassPath

object ReBootContext {
    val contextMap: MutableMap<String, Any> = mutableMapOf()

    fun run() {
        println("start...")
        val classLoader = Thread.currentThread().contextClassLoader
        val classes = ClassPath.from(classLoader).topLevelClasses
            .mapNotNull {
                runCatching { it.load() }.getOrNull()
            }

        classes.filter { it.isAnnotationPresent(Component::class.java) }
            .forEach { init(it) }

        println(contextMap)

        val scheduleProcessor = contextMap[ScheduleProcessor::class.java.name] as ScheduleProcessor
        scheduleProcessor.process()
    }

    private fun init(it: Class<*>): Any =
        when (val instance = contextMap[it.name]) {
            null -> {
                val constructor = it.declaredConstructors.first()
                val parameterTypes = constructor.parameterTypes
                constructor.newInstance(*parameterTypes.map { init(it) }.toTypedArray()).apply {
                    contextMap[it.name] = this
                }
            }
            else -> instance
        }
}
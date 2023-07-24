package org.reboot.app

import java.lang.reflect.Method
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ScheduleProcessor {
    fun process() {
        val executor = Executors.newScheduledThreadPool(10)
        val classes = ReBootContext.contextMap.map { bean ->
            bean.value.javaClass.declaredMethods.filter { method ->
                method.isAnnotationPresent(Schedule::class.java)
            } to bean.value
        }
        if (classes.isEmpty()) return
        classes.forEach { methods: Pair<List<Method>, Any> ->
            methods.first.forEach { method ->
                executor.scheduleAtFixedRate(
                    { method.invoke(methods.second) },
                    0, 1, TimeUnit.SECONDS
                )
            }
        }
    }
}
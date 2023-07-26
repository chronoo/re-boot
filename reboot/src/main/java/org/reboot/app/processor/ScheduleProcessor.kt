package org.reboot.app.processor

import org.reboot.app.annotation.Component
import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Schedule
import org.reboot.app.utils.getAnnotatedMethods
import java.lang.reflect.Method
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ScheduleProcessor : Processor {
    override fun process() {
        val executor = Executors.newScheduledThreadPool(10)
        val methodPairs = ReBootContext.contextMap.map { bean ->
            bean.value.clazz.getAnnotatedMethods<Schedule>()
                .map { Scheduled(it, it.getAnnotation(Schedule::class.java)) } to bean.value.bean
        }
        methodPairs.forEach { methodPair: Pair<List<Scheduled>, Any> ->
            methodPair.first.forEach { scheduled ->
                executor.scheduleAtFixedRate(
                    { scheduled.method.invoke(methodPair.second) },
                    0,
                    scheduled.schedule.period,
                    TimeUnit.MILLISECONDS
                )
            }
        }
    }

    data class Scheduled(
        val method: Method,
        val schedule: Schedule
    )
}


package org.reboot.app.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Schedule(
    val period: Long = 1000
)

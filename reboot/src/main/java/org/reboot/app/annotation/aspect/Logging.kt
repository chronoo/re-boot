package org.reboot.app.annotation.aspect

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Logging(
    val order: Int = 0
)

package org.reboot.app.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Logging(
    val order: Int = 0
)

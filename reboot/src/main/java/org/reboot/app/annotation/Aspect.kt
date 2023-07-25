package org.reboot.app.annotation

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Aspect(
    val annotation: KClass<out Annotation>,
    val order: Int = 0
)

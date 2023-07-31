package org.reboot.app.utils

import java.lang.reflect.Method

inline fun <reified T: Annotation> Class<*>.hasAnnotatedMethod() =
    declaredMethods.any { method ->
        method.isAnnotationPresent(T::class.java)
    }

inline fun <reified T: Annotation> Class<*>.getAnnotatedMethods(): List<Method> =
    declaredMethods.filter { method ->
        method.isAnnotationPresent(T::class.java)
    }

val Class<*>.firstConstructor
    get() = declaredConstructors.firstOrNull()

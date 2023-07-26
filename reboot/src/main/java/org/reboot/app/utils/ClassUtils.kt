package org.reboot.app.utils

inline fun <reified T: Annotation> Class<*>.hasAnnotatedMethod() =
    declaredMethods.any { method ->
        method.isAnnotationPresent(T::class.java)
    }

inline fun <reified T: Annotation> Class<*>.getAnnotatedMethods() =
    declaredMethods.filter { method ->
        method.isAnnotationPresent(T::class.java)
    }

val Class<*>.firstConstructor
    get() = declaredConstructors.firstOrNull()

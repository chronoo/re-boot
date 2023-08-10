package org.rebase

import kotlin.collections.mapNotNull
import kotlin.runCatching

object ClassLocator {
    fun findAllClasses(): List<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader
        val classes = com.google.common.reflect.ClassPath.from(classLoader).topLevelClasses
            .mapNotNull {
                runCatching { it.load() }.getOrNull()
            }
        return classes
    }
}

package org.reboot.app.processor

import com.google.common.reflect.ClassPath
import java.io.File
import java.util.*

object PropertyReader {
    fun readProperties(): List<ResourceBundle> {
        val classLoader = Thread.currentThread().contextClassLoader
        return ClassPath.from(classLoader).resources
            .filter { !it.resourceName.contains('/') && it.resourceName.endsWith(".properties") }
            .map { File(it.resourceName).nameWithoutExtension }
            .toSet()
            .map { ResourceBundle.getBundle(it) }
    }
}
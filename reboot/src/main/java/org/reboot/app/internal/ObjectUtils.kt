package org.reboot.app.internal

import com.google.common.base.CaseFormat
import org.reboot.app.annotation.Config
import java.util.*

fun Any.fillProps(properties: List<ResourceBundle>, annotation: Config) {
    this.javaClass.declaredFields.forEach { field ->
        field.isAccessible = true
        val value = properties.firstNotNullOf { bundle ->
            runCatching {
                bundle.getString("${annotation.prefix}.${field.name.turnCase()}")
            }.getOrNull()
        }
        field.set(
            this, when (field.type) {
                String::class.java -> value
                Int::class.java -> value.toInt()
                Long::class.java -> value.toLong()
                else -> throw IllegalAccessException()
            }
        )
    }
}

private fun String.turnCase() = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this)

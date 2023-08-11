package org.redata.ddl

import org.redata.ddl.definition.Column
import org.redata.ddl.definition.ColumnDefinition
import org.redata.ddl.definition.ReEntity
import org.redata.ddl.definition.TableDefinition

object TableDefinitionLocator {
    fun locate(allClasses: List<Class<*>>): List<TableDefinition> {
        val entities = allClasses.filter {
            it.isAnnotationPresent(ReEntity::class.java)
        }
        return entities.map { createTableDefinition(it) }
    }

    private fun createTableDefinition(clazz: Class<*>):TableDefinition {
        val entity = clazz.getAnnotation(ReEntity::class.java)
        val columnsDefinitions = getColumnsDefinitions(clazz)
        return TableDefinition(entity.value, columnsDefinitions)
    }

    private fun getColumnsDefinitions(clazz: Class<*>): List<ColumnDefinition> {
        val columnsDefinition = clazz.constructors.first()
            .parameters.map { parameter ->
                if (parameter.annotations.isNotEmpty()) {
                    val columnAnnotation = parameter.getAnnotation(Column::class.java)
                    ColumnDefinition(columnAnnotation.value, columnAnnotation.type)
                } else {
                    ColumnDefinition(parameter.name, parameter.type.asString)
                }
            }

        if (columnsDefinition.isEmpty()) {
            return clazz.declaredFields.map { field ->
                if (field.isAnnotationPresent(Column::class.java)) {
                    val columnAnnotation = field.getAnnotation(Column::class.java)
                    ColumnDefinition(columnAnnotation.value, columnAnnotation.type)
                } else {
                    ColumnDefinition(field.name, field.type.asString)
                }
            }
        }
        return columnsDefinition.ifEmpty { listOf() }
    }
}

private val <T> Class<T>.asString: String
    get() {
        return when(this) {
            String::class.java -> "text"
            Int::class.java, Long::class.java -> "number"
            else -> throw IllegalArgumentException("unexpected type")
        }
    }

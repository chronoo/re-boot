package org.redata.ddl

import org.rebase.instance
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

    private fun createTableDefinition(clazz: Class<*>): TableDefinition {
        val entity = clazz.getAnnotation(ReEntity::class.java)
        val columnsDefinitions = getColumnsDefinitions(clazz)
        return TableDefinition(entity.value, columnsDefinitions)
    }

    private fun getColumnsDefinitions(clazz: Class<*>): List<ColumnDefinition> {
        val columnsDefinition = extractColumnDefinition(
            clazz.constructors.first().parameters
        )
        if (columnsDefinition.isEmpty()) {
            return extractColumnDefinition(clazz.declaredFields)
        }
        return columnsDefinition.ifEmpty { listOf() }
    }

    private fun <T : Any> extractColumnDefinition(fields: Array<out T>): List<ColumnDefinition> {
        return fields.map { it instance SourceElement::class }
            .map { parameter ->
                if (parameter.isAnnotationPresent(Column::class.java)) {
                    val columnAnnotation = parameter.getAnnotation(Column::class.java)
                    ColumnDefinition(columnAnnotation.value, columnAnnotation.type)
                } else {
                    ColumnDefinition(parameter.name, parameter.type.asString)
                }
            }
    }

    interface SourceElement {
        fun isAnnotationPresent(annotationClass: Class<out Annotation>): Boolean
        fun <T : Annotation> getAnnotation(annotationClass: Class<T>): T
        var name: String
        var type: Class<*>
    }
}


package org.redata.ddl

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
        return TableDefinition(entity.value, listOf())
    }
}

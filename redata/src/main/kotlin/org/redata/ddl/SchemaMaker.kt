package org.redata.ddl

import com.google.common.reflect.ClassPath
import org.redata.ddl.definition.TableDefinition
import java.sql.Connection

object SchemaMaker {
    fun init(connection: Connection, tableDefinitions: List<TableDefinition>) {
        for (tableDefinition in tableDefinitions) {
            TableMaker.make(connection, tableDefinition)
        }
    }
}

object TableDefinitionLocator {
    fun locate(): List<TableDefinition> {
        return listOf()
    }
}

object ClassLocator {
    fun locate(): List<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader
        val classes = ClassPath.from(classLoader).topLevelClasses
            .mapNotNull {
                runCatching { it.load() }.getOrNull()
            }
        return classes
    }
}
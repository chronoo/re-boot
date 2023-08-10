package org.redata.ddl

import org.rebase.ClassLocator

object DBInitializer {
    fun init() {
        val allClasses = ClassLocator.findAllClasses()
        TableDefinitionLocator.locate(allClasses)
    }
}

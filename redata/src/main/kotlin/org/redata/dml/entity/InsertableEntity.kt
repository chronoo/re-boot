package org.redata.dml.entity

interface InsertableEntity {
    fun toInsertSql(): String
}
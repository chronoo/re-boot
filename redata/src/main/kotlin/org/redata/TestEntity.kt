package org.redata

import org.intellij.lang.annotations.Language
import org.redata.dml.entity.InsertableEntity

data class TestEntity(
    val name: String,
    val age: Int
) : InsertableEntity {
    @Language("SQL")
    override fun toInsertSql() =
        "INSERT INTO TEST(NAME, AGE) VALUES ('$name', $age)" // TODO adopt to prepareStatement
}
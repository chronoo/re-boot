package org.redata.ddl

val <T> Class<T>.asString: String
    get() = when (this) {
        String::class.java -> "text"
        Int::class.java, Long::class.java -> "number"
        else -> throw IllegalArgumentException("unexpected type")
    }

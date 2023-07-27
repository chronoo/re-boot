package org.example.mock

fun <T> assertEquals(expected: T, actual: T, message: String) {
    if (expected != actual) {
        throw Error(message)
    }
}

fun <T> assertEquals(expected: T, actual: T) =
    assertEquals(expected, actual, "Value is not equals: \n\tExpected: [$expected]\n\tActual: [$actual]")

package org.example.mock

fun <T> assertEquals(expected: T, actual: T, message: String) {
    if (expected != actual) {
        throw Error(message)
    }
}

fun <T> assertNotEquals(expected: T, actual: T, message: String) {
    if (expected == actual) {
        throw Error(message)
    }
}

fun <T> assertEquals(expected: T, actual: T) =
    assertEquals(expected, actual, "Value not equals: \n\tExpected: [$expected]\n\tActual: [$actual]")

fun <T> assertNotEquals(expected: T, actual: T) =
    assertNotEquals(expected, actual, "Value equals: \n\tExpected: [$expected]\n\tActual: [$actual]")

infix fun <T> T.equals(expected: T) =
    assertEquals(expected, this)

infix fun <T> T.notEquals(expected: T) =
    assertNotEquals(expected, this)
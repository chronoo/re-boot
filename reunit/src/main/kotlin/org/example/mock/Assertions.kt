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

infix fun <T> T.shouldBe(expected: T) =
    assertEquals(expected, this)

infix fun <T> T.shouldNotBe(expected: T) =
    assertNotEquals(expected, this)

val Boolean.isTrue
    get() = assertEquals(true, this)

val Boolean.isFalse
    get() = assertEquals(false, this)

val Any?.isNull
    get() = assertEquals(null, this)

fun <T> that(value: T) =
    Asserted(value)

inline fun <reified E: Throwable> shouldThrow(action: () -> Unit): E {
    try {
        action()
    } catch (e: Throwable) {
        if (e is E) return e
    }
    throw Error("Error expected, but not appeared")
}

fun failed(action: () -> Unit) =
    shouldThrow<Throwable>(action)

class Asserted<T>(private val value: T) {
    fun shouldBe(expected: T) = value shouldBe expected
}

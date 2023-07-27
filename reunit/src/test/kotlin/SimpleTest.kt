import org.example.mock.assertEquals
import org.example.mock.runTests
import org.example.mock.test

fun main() = runTests {
    test("success test") {
        assertEquals(42, 42)
    }

    test("fail test2") {
        assertEquals(42, 41)
    }
}

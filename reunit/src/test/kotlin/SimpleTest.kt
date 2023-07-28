import org.example.mock.*

fun main() = runTests("Assertions") {
    test("success test") {
        42 equals 42
    }

    test("fail test") {
        assertEquals(42, 41)
    }

    test("infix equals fail") {
        42 equals 41
    }

    test("infix not equals") {
        42 notEquals 41
    }
}

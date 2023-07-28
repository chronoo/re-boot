import org.example.mock.*

fun main() = runTests("Assertions") {
    test("success test") {
        42 equals 42
    }

    test("fail test") {
        failed {
            assertEquals(42, 41)
        }
    }

    test("infix equals fail") {
        failed {
            42 equals 41
        }
    }

    test("infix not equals") {
        42 notEquals 41
    }

    test("boolean assert") {
        true.isTrue
    }

    test("boolean assert") {
        failed {
            true.isFalse
        }
    }

    test("assertThat assert") {
        assertThat(42).isEquals(42)

        failed {
            assertThat(42).isEquals(41)
        }
    }
}

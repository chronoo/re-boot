import org.example.mock.*

fun main() = runTests("Assertions") {
    test("success test") {
        42 shouldBe 42
    }

    test("fail test") {
        failed {
            assertEquals(42, 41)
        }
    }

    test("infix equals fail") {
        failed {
            42 shouldBe 41
        }
    }

    test("infix not equals") {
        42 shouldNotBe 41
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
        that(42).shouldBe(42)

        failed {
            that(42).shouldBe(41)
        }
    }

    test("exception assert") {
        shouldThrow<IllegalStateException> {
            throw IllegalStateException("test message")
        }.message shouldBe "test message"

        failed {
            shouldThrow<IllegalStateException> {
                throw NullPointerException()
            }
        }
    }
}

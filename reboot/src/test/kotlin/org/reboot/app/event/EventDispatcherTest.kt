package org.reboot.app.event

import org.example.mock.runTests
import org.example.mock.shouldBe
import org.example.mock.test

fun main() = runTests("EventDispatcher") {
    test("process event") {
        val events = mutableListOf<CustomEvent>()
        EventDispatcher.subscribe<CustomEvent> {
            events += it
        }
        EventDispatcher.emit(CustomEvent("test-event"))
        events.size shouldBe 1
        events.first().name shouldBe "test-event"
    }
}

class CustomEvent(name: String) : Event(name)
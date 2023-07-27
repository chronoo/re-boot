package org.reboot.app.event

import org.example.mock.assertEquals
import org.example.mock.runTests
import org.example.mock.test

fun main() = runTests("EventDispatcher") {
    test("process event") {
        val events = mutableListOf<Event>()
        EventDispatcher.subscribe(CustomEvent::class.java) {
            events += it
        }
        EventDispatcher.emit(CustomEvent("test-event"))
        assertEquals(1, events.size)
        assertEquals("test-event", events.first().name)
    }
}

class CustomEvent(name: String) : Event(name)
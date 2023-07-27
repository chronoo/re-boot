package org.reboot.app.event

object EventDispatcher {
    private val listeners: MutableMap<Class<out Event>, MutableList<(event: Event) -> Unit>> = mutableMapOf()

    fun subscribe(clazz: Class<out Event>, callback: (event: Event) -> Unit) {
        listeners.computeIfAbsent(clazz) { mutableListOf() }
            .add(0, callback)
        println("register listener to $clazz")
    }

    fun emit(event: Event) {
        println("submit event $event")
        listeners.entries.filter { it.key.isInstance(event) }
            .flatMap { it.value }
            .forEach { listener ->
                listener(event)
            }
    }
}
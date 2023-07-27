package org.reboot.app.event

object EventDispatcher {
    val listeners: MutableMap<Class<out Event>, MutableList<(event: Event) -> Unit>> = mutableMapOf()

    inline fun <reified T : Event> subscribe(noinline callback: (event: T) -> Unit) {
        val subscribers = listeners.computeIfAbsent(T::class.java) { mutableListOf() }
        subscribers += callback as (Event) -> Unit
        println("register listener to ${T::class.java}")
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
package org.reboot.app.event

object EventDispatcher {
    private val listeners: MutableMap<Class<out Event>, MutableList<(event: Event) -> Unit>> = mutableMapOf()

    inline fun <reified T : Event> subscribe(noinline callback: (event: T) -> Unit) =
        subscribe(T::class.java, callback)

    fun <T: Event> subscribe(clazz: Class<T>, callback: (event: T) -> Unit) {
        val subscribers = listeners.computeIfAbsent(clazz) { mutableListOf() }
        subscribers += callback as (Event) -> Unit
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
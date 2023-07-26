package web

import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener
import org.reboot.app.event.Event
import org.reboot.app.event.EventDispatcher

@Component
class WebComponent(
    private val config: WebConfig
) : Maker {

    @EventListener
    fun doIt(event: CustomEvent) {
        println("was processed event ${event.name}")
    }

    override fun make() {
        EventDispatcher.emit(
            CustomEvent("${config.fullName}:${config.message}")
        )
    }

    class CustomEvent(name: String) : Event(name)
}

package web

import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener
import org.reboot.app.annotation.aspect.Logging
import org.reboot.app.event.Event

@Logging
@Component
class EventMessageListener {
    @EventListener
    fun onMessage(event: Event) {
        println("also was processed event ${event.name}")
    }
}
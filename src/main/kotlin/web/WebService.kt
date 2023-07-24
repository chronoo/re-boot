package web

import org.reboot.app.Component
import org.reboot.app.Schedule

@Component
class WebService(private val component: WebComponent) {
    @Schedule
    fun doIt() {
        component.make()
    }
}
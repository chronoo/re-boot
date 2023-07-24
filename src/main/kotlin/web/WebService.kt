package web

import org.reboot.app.annotation.Component
import org.reboot.app.annotation.Schedule

@Component
class WebService(private val component: WebComponent) {
    @Schedule(period = 3000)
    fun doIt() {
        component.make()
    }
}
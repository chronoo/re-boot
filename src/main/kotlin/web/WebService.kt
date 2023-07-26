package web

import org.reboot.app.annotation.Component
import org.reboot.app.annotation.aspect.Logging
import org.reboot.app.annotation.Schedule

@Component
class WebService(
    private val component: Maker,
    private val config: WebConfig
) {
    @Schedule(period = 3000)
    fun doIt() {
        component.make()
    }

    @Schedule(period = 1000)
    fun doThat() {
        println("port is ${config.port}")
    }
}
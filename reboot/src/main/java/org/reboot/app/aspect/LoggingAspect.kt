package org.reboot.app.aspect

import net.sf.cglib.proxy.MethodProxy
import org.reboot.app.annotation.Aspect
import org.reboot.app.annotation.Logging
import java.lang.reflect.Method

@Aspect(Logging::class)
object LoggingAspect : AspectProcessor {
    override fun before(obj: Any?, method: Method?, args: Array<out Any>?, proxy: MethodProxy?) {
        println("before")
    }

    override fun after(obj: Any?, method: Method?, args: Array<out Any>?, proxy: MethodProxy?) {
        println("after")
    }
}

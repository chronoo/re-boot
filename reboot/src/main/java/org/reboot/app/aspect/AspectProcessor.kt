package org.reboot.app.aspect

import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method

interface AspectProcessor {
    fun before(obj: Any?, method: Method?, args: Array<out Any>?, proxy: MethodProxy?)
    fun after(obj: Any?, method: Method?, args: Array<out Any>?, proxy: MethodProxy?)
}
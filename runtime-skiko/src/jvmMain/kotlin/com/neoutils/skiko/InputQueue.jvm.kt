package com.neoutils.skiko

import com.neoutils.core.input.InputEvent
import java.util.concurrent.ConcurrentLinkedQueue

actual class InputQueue {

    // AWT enqueues on the EDT; Skiko drains on the render thread.
    private val queue = ConcurrentLinkedQueue<InputEvent>()

    actual fun add(event: InputEvent) {
        queue.add(event)
    }

    actual fun poll(): InputEvent? = queue.poll()
}

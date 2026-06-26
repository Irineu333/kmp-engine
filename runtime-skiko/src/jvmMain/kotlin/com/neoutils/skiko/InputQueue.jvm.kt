package com.neoutils.skiko

import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.InputQueue
import java.util.concurrent.ConcurrentLinkedQueue

private class JvmInputQueue : InputQueue {

    // AWT enqueues on the EDT; Skiko drains on the render thread.
    private val queue = ConcurrentLinkedQueue<InputEvent>()

    override fun add(event: InputEvent) {
        queue.add(event)
    }

    override fun poll(): InputEvent? = queue.poll()
}

actual fun createInputQueue(): InputQueue = JvmInputQueue()

package com.neoutils.skiko

import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.InputQueue

private class WasmInputQueue : InputQueue {

    // The browser is single-threaded: DOM events and rendering share one thread.
    private val queue = ArrayDeque<InputEvent>()

    override fun add(event: InputEvent) {
        queue.addLast(event)
    }

    override fun poll(): InputEvent? = queue.removeFirstOrNull()
}

actual fun createInputQueue(): InputQueue = WasmInputQueue()

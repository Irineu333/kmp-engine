package com.neoutils.skiko

import com.neoutils.core.input.InputEvent

actual class InputQueue {

    // The browser is single-threaded: DOM events and rendering share one thread.
    private val queue = ArrayDeque<InputEvent>()

    actual fun add(event: InputEvent) {
        queue.addLast(event)
    }

    actual fun poll(): InputEvent? = queue.removeFirstOrNull()
}

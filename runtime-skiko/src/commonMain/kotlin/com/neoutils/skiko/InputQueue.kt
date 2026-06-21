package com.neoutils.skiko

import com.neoutils.core.input.InputEvent

expect class InputQueue() {
    fun add(event: InputEvent)
    fun poll(): InputEvent?
}

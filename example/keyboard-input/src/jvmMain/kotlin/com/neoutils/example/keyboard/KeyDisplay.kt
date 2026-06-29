package com.neoutils.example.keyboard

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.node.Label

class KeyDisplay : Label() {

    override fun onReady() {
        text = "Press any key"
    }

    override fun onProcess(delta: Float) {
        position = Viewport.size.center(getSize())
    }

    override fun onInput(event: InputEvent) {
        if (event is KeyEvent) {
            text = event.key.toString()
            color = if (event.pressed) Color.GREEN else Color.GRAY
        }
    }
}

package com.neoutils.example.keyboard

import com.neoutils.core.graphics.Color
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Label

class KeyDisplay : Label() {

    override fun onReady() {
        text = "Press any key"
    }

    override fun onProcess(delta: Float) {
        val viewport = engine?.size ?: return
        val size = getSize()

        position = Vec2(
            x = (viewport.width - size.width) / 2f,
            y = (viewport.height - size.height) / 2f,
        )
    }

    override fun onInput(event: InputEvent) {
        if (event is KeyEvent) {
            text = event.key.toString()
            color = if (event.pressed) Color.GREEN else Color.GRAY
        }
    }
}

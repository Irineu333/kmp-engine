package com.neoutils.example.pong.menu

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.math.Vec2
import com.neoutils.core.node.Label

class MenuHint : Label() {

    override fun onReady() {
        text = "UP/DOWN to navigate • ENTER to select"
        fontSize = 16f
        color = Color.GRAY
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size
        val size = getSize()
        position = Vec2(
            x = viewport.center(size).x,
            y = viewport.height / 2f + OFFSET,
        )
    }

    companion object {
        // Sits below the viewport-centred options block.
        private const val OFFSET = 70f
    }
}

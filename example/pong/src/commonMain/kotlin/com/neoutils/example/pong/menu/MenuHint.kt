package com.neoutils.example.pong.menu

import com.neoutils.core.graphics.Color
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Label

class MenuHint : Label() {

    override fun onReady() {
        text = "UP/DOWN to navigate • ENTER to select"
        fontSize = 16f
        color = Color.GRAY
    }

    override fun onProcess(delta: Float) {
        val viewport = tree?.size ?: return
        val size = getSize()
        position = Vec2(
            x = (viewport.width - size.width) / 2f,
            y = viewport.height / 2f + OFFSET,
        )
    }

    companion object {
        // Sits below the viewport-centred options block.
        private const val OFFSET = 70f
    }
}

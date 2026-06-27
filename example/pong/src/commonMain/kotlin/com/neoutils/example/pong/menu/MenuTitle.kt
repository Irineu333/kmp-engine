package com.neoutils.example.pong.menu

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Label

class MenuTitle : Label() {

    override fun onReady() {
        text = "PONG"
        fontSize = 96f
        color = Color.WHITE
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size
        val size = getSize()
        position = Vec2(
            x = (viewport.width - size.width) / 2f,
            y = viewport.height / 2f - size.height - OFFSET,
        )
    }

    companion object {
        // Sits above the viewport-centred options block.
        private const val OFFSET = 70f
    }
}

package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node

class Net : Node() {

    var color: Color = Color.GRAY

    override fun onDraw(renderer: Renderer) {
        val viewport = engine?.size ?: return
        val x = viewport.width / 2f

        var y = 0f
        while (y < viewport.height) {
            renderer.drawLine(
                start = Vec2(x, y),
                end = Vec2(x, y + DASH),
                color = color,
                width = WIDTH,
            )
            y += DASH + GAP
        }
    }

    companion object {
        private const val DASH = 16f
        private const val GAP = 12f
        private const val WIDTH = 4f
    }
}

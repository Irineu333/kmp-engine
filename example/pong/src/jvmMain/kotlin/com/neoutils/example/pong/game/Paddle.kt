package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Key
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node2D

class Paddle : Node2D() {

    var side: Side = Side.LEFT
    var upKey: Key = Key.W
    var downKey: Key = Key.S
    var color: Color = Color.WHITE

    private val halfWidth get() = WIDTH / 2f
    private val halfHeight get() = HEIGHT / 2f

    override fun onReady() {
        val viewport = tree?.size ?: return
        position = Vec2(anchorX(viewport.width), viewport.height / 2f)
    }

    override fun onProcess(delta: Float) {
        val viewport = tree?.size ?: return

        var y = position.y
        tree?.input?.let { input ->
            if (input.isPressed(upKey)) y -= SPEED * delta
            if (input.isPressed(downKey)) y += SPEED * delta
        }

        // Re-anchor x every frame so the right paddle tracks the edge on resize.
        position = Vec2(
            x = anchorX(viewport.width),
            y = y.coerceIn(halfHeight, viewport.height - halfHeight),
        )
    }

    private fun anchorX(width: Float): Float =
        if (side == Side.LEFT) MARGIN + halfWidth else width - MARGIN - halfWidth

    override fun bounds(): Rect = Rect(
        position = Vec2(-halfWidth, -halfHeight),
        size = Size(WIDTH, HEIGHT),
    )

    override fun onDraw(renderer: Renderer) {
        val rect = globalBounds() ?: return
        renderer.drawRect(rect, color, fill = true)
    }

    companion object {
        private const val WIDTH = 16f
        private const val HEIGHT = 100f
        private const val MARGIN = 32f
        private const val SPEED = 420f
    }
}

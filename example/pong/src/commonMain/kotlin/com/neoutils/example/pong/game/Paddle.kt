package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.Key
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Shape
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2
import com.neoutils.core.node.Collider
import com.neoutils.core.node.Node2D

class Paddle : Node2D() {

    var side: Side = Side.LEFT
    var controller: PaddleController = HumanController(Key.W, Key.S)
    var color: Color = Color.WHITE

    private val halfWidth get() = WIDTH / 2f
    private val halfHeight get() = HEIGHT / 2f

    init {
        // Detected by the ball's collider; the paddle itself doesn't react.
        add(Collider(Shape.Aabb(Size(WIDTH, HEIGHT))))
    }

    override fun onReady() {
        val viewport = Viewport.size
        position = Vec2(anchorX(viewport.width), viewport.height / 2f)
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size

        val y = position.y + controller.direction(this) * SPEED * delta

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

package com.neoutils.example.bouncing

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.Node2D
import com.neoutils.core.math.Rect
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2

class Ball : Node2D() {

    var radius: Float = 32f
    var color: Color = Color.RED

    var velocity = Vec2(260f, 200f)
        private set

    override fun onReady() {
        val viewport = tree?.size ?: return

        position = Vec2(viewport.width / 2f, viewport.height / 2f)
    }

    override fun onProcess(delta: Float) {
        val viewport = tree?.size ?: return

        val next = position + velocity * delta

        velocity = Vec2(
            reflect(next.x, velocity.x, viewport.width),
            reflect(next.y, velocity.y, viewport.height),
        )

        position = Vec2(
            next.x.coerceIn(radius, viewport.width - radius),
            next.y.coerceIn(radius, viewport.height - radius),
        )
    }

    private fun reflect(value: Float, velocity: Float, limit: Float): Float {
        return if (value - radius <= 0f || value + radius >= limit) -velocity else velocity
    }

    override fun bounds(): Rect = Rect(
        position = Vec2(-radius, -radius),
        size = Size(radius * 2f, radius * 2f),
    )

    override fun onDraw(renderer: Renderer) {
        renderer.drawCircle(globalPosition(), radius, color)
    }
}

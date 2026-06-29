package com.neoutils.example.bouncing

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.node.Node2D
import com.neoutils.core.math.Rect
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2

class Ball : Node2D() {

    var radius: Float = 32f
    var color: Color = Color.RED

    var velocity = Vec2.randomVelocity(320f)
        private set

    override fun onReady() {
        val viewport = Viewport.size

        position = Vec2(viewport.width / 2f, viewport.height / 2f)
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size

        val next = position + velocity * delta

        if (next.x - radius <= 0f || next.x + radius >= viewport.width) {
            velocity = velocity.reflect(Vec2(1f, 0f))
        }
        if (next.y - radius <= 0f || next.y + radius >= viewport.height) {
            velocity = velocity.reflect(Vec2(0f, 1f))
        }

        position = Vec2(
            next.x.coerceIn(radius, viewport.width - radius),
            next.y.coerceIn(radius, viewport.height - radius),
        )
    }

    override fun bounds(): Rect = Rect(
        position = Vec2(-radius, -radius),
        size = Size(radius * 2f, radius * 2f),
    )

    override fun onDraw(renderer: Renderer) {
        renderer.drawCircle(globalPosition(), radius, color)
    }
}

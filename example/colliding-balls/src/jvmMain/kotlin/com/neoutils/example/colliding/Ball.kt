package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2
import com.neoutils.core.node.Node2D
import kotlin.random.Random

class Ball : Node2D() {

    var radius: Float = RADIUS
    var color: Color = Color.RED
    var velocity: Vec2 = Vec2.ZERO

    val mass: Float get() = radius * radius

    override fun onReady() {
        val viewport = Viewport.size

        color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
        position = Vec2.random() * Vec2(viewport.width - 2 * radius, viewport.height - 2 * radius) + Vec2(radius, radius)
        velocity = Vec2.randomVelocity(MIN_SPEED + Random.nextFloat() * (MAX_SPEED - MIN_SPEED))
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size

        position += velocity * delta
        bounceOffWalls(viewport)
    }

    private fun bounceOffWalls(viewport: Size) {
        if (position.x - radius < 0f || position.x + radius > viewport.width) {
            velocity = velocity.reflect(Vec2(1f, 0f))
        }
        if (position.y - radius < 0f || position.y + radius > viewport.height) {
            velocity = velocity.reflect(Vec2(0f, 1f))
        }
        position = Vec2(
            position.x.coerceIn(radius, viewport.width - radius),
            position.y.coerceIn(radius, viewport.height - radius),
        )
    }

    override fun bounds(): Rect = Rect(
        position = Vec2(-radius, -radius),
        size = Size(radius * 2f, radius * 2f),
    )

    override fun onDraw(renderer: Renderer) {
        renderer.drawCircle(globalPosition(), radius, color)
    }

    companion object {
        private const val RADIUS = 28f
        private const val MIN_SPEED = 150f
        private const val MAX_SPEED = 300f
    }
}

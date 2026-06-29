package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2
import com.neoutils.core.node.Node2D
import kotlin.random.Random

class Ball : Node2D() {

    val radius: Float = RADIUS
    var velocity: Vec2 = Vec2.ZERO
    var color: Color = Color.WHITE

    private var serving = true
    private var serveTimer = 0f

    override fun onReady() {
        serve()
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size

        if (serving) {
            position = Vec2(viewport.width / 2f, viewport.height / 2f)
            serveTimer += delta
            if (serveTimer >= SERVE_DELAY) launch()
            return
        }

        position += velocity * delta

        if (position.y - radius < 0f || position.y + radius > viewport.height) {
            velocity = velocity.reflect(Vec2(0f, 1f))
            position = position.copy(y = position.y.coerceIn(radius, viewport.height - radius))
        }
    }

    /** Resets the ball to the center; it serves itself after [SERVE_DELAY]. */
    fun serve() {
        serving = true
        serveTimer = 0f
        velocity = Vec2.ZERO
        val viewport = Viewport.size
        position = Vec2(viewport.width / 2f, viewport.height / 2f)
    }

    private fun launch() {
        serving = false
        val directionX = if (Random.nextBoolean()) 1f else -1f
        val vertical = (Random.nextFloat() * 2f - 1f) * 0.6f
        velocity = Vec2(directionX, vertical).normalized() * SPEED
    }

    override fun bounds(): Rect = Rect(
        position = Vec2(-radius, -radius),
        size = Size(radius * 2f, radius * 2f),
    )

    override fun onDraw(renderer: Renderer) {
        renderer.drawCircle(globalPosition(), radius, color)
    }

    companion object {
        private const val RADIUS = 12f
        private const val SPEED = 420f
        private const val SERVE_DELAY = 1f
    }
}

package com.neoutils.example.bouncing

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Debug overlay that walks the whole scene tree and draws the velocity vector
 * of every [Ball] it finds, no matter where it sits in the tree.
 *
 * The arrow starts at the ball's global position and its length is the velocity
 * scaled by [scale]; with `scale = 1f` it shows where the ball travels in one
 * second, so smaller values keep the arrow compact on screen.
 */
class VelocityOverlay : Node() {

    var color: Color = Color.GREEN
    var scale: Float = 0.2f
    var width: Float = 2f
    var headLength: Float = 12f

    override fun onDraw(renderer: Renderer) {
        val root = tree?.root ?: return
        drawVelocities(root, renderer)
    }

    private fun drawVelocities(node: Node, renderer: Renderer) {
        if (node is Ball) {
            drawArrow(node, renderer)
        }

        node.children.forEach {
            drawVelocities(it, renderer)
        }
    }

    private fun drawArrow(ball: Ball, renderer: Renderer) {
        val origin = ball.globalPosition()
        val velocity = ball.velocity * scale

        val length = sqrt(velocity.x * velocity.x + velocity.y * velocity.y)
        if (length == 0f) return

        val tip = origin + velocity

        renderer.drawLine(origin, tip, color, width)

        // Arrowhead: two short segments splayed back from the tip.
        val angle = atan2(velocity.y, velocity.x)
        val spread = 0.5f

        val left = tip - Vec2(cos(angle - spread), sin(angle - spread)) * headLength
        val right = tip - Vec2(cos(angle + spread), sin(angle + spread)) * headLength

        renderer.drawLine(tip, left, color, width)
        renderer.drawLine(tip, right, color, width)
    }
}

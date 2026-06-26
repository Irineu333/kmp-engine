package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Key
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node
import com.neoutils.debug.DebugFeature
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class VelocityOverlay : DebugFeature(shortcut = Key.F3, enabled = false) {

    var color: Color = Color.WHITE
    var scale: Float = 0.2f
    var width: Float = 2f
    var headLength: Float = 12f

    override fun draw(renderer: Renderer) {
        drawVelocities(engine?.root ?: return, renderer)
    }

    private fun drawVelocities(node: Node, renderer: Renderer) {
        if (node is Ball) drawArrow(node, renderer)
        node.children.forEach { drawVelocities(it, renderer) }
    }

    private fun drawArrow(ball: Ball, renderer: Renderer) {
        val velocity = ball.velocity * scale
        if (velocity.length() == 0f) return

        val origin = ball.globalPosition()
        val tip = origin + velocity

        renderer.drawLine(origin, tip, color, width)

        val angle = atan2(velocity.y, velocity.x)
        val spread = 0.5f

        val left = tip - Vec2(cos(angle - spread), sin(angle - spread)) * headLength
        val right = tip - Vec2(cos(angle + spread), sin(angle + spread)) * headLength

        renderer.drawLine(tip, left, color, width)
        renderer.drawLine(tip, right, color, width)
    }
}

package com.neoutils.example.pong.game

import com.neoutils.core.input.Input
import com.neoutils.core.input.Key
import com.neoutils.core.scene.Node
import kotlin.math.abs

/** Per-frame vertical intent for a [Paddle]: -1f up, +1f down, 0f idle. */
fun interface PaddleController {
    fun direction(paddle: Paddle): Float
}

class HumanController(
    private val upKey: Key,
    private val downKey: Key,
) : PaddleController {

    override fun direction(paddle: Paddle): Float {
        var direction = 0f
        if (Input.isPressed(upKey)) direction -= 1f
        if (Input.isPressed(downKey)) direction += 1f
        return direction
    }
}

/**
 * Follows the ball, easing off as it closes in so the paddle glides into place
 * instead of jittering. Larger [responseDistance] means a gentler, more beatable AI.
 */
class AIController(
    private val responseDistance: Float = 90f,
    private val deadZone: Float = 6f,
) : PaddleController {

    private var ball: Ball? = null

    override fun direction(paddle: Paddle): Float {
        val ball = ball ?: findBall(paddle.tree?.root ?: return 0f)?.also { ball = it } ?: return 0f
        val diff = ball.globalPosition().y - paddle.globalPosition().y
        if (abs(diff) <= deadZone) return 0f
        return (diff / responseDistance).coerceIn(-1f, 1f)
    }

    private fun findBall(node: Node): Ball? {
        if (node is Ball) return node
        node.children.forEach { child -> findBall(child)?.let { return it } }
        return null
    }
}

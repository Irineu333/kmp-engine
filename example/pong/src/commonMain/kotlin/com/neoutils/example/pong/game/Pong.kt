package com.neoutils.example.pong.game

import com.neoutils.core.input.Key
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node

class Pong : Node() {

    private var leftPaddle: Paddle? = null
    private var rightPaddle: Paddle? = null
    private var ball: Ball? = null
    private var leftBoard: ScoreBoard? = null
    private var rightBoard: ScoreBoard? = null

    private var leftScore = 0
    private var rightScore = 0

    override fun onReady() {
        collect(parent ?: return)
        applyMode(engine?.args as? GameMode ?: GameMode.PLAYER_VS_PLAYER)
    }

    private fun applyMode(mode: GameMode) {
        leftPaddle?.controller = when (mode) {
            GameMode.AI_VS_AI -> AIController()
            else -> HumanController(Key.W, Key.S)
        }
        rightPaddle?.controller = when (mode) {
            GameMode.PLAYER_VS_PLAYER -> HumanController(Key.UP, Key.DOWN)
            else -> AIController()
        }
    }

    override fun onProcess(delta: Float) {
        val viewport = engine?.size ?: return
        val ball = ball ?: return

        leftPaddle?.let { bounceOffPaddle(ball, it) }
        rightPaddle?.let { bounceOffPaddle(ball, it) }

        val center = ball.globalPosition()
        when {
            center.x + ball.radius < 0f -> score(rightPlayer = true)
            center.x - ball.radius > viewport.width -> score(rightPlayer = false)
        }
    }

    private fun bounceOffPaddle(ball: Ball, paddle: Paddle) {
        val movingLeft = ball.velocity.x < 0f
        val towardThisPaddle = (paddle.side == Side.LEFT) == movingLeft
        if (!towardThisPaddle) return

        val rect = paddle.globalBounds() ?: return
        val center = ball.globalPosition()

        val closest = Vec2(
            x = center.x.coerceIn(rect.position.x, rect.position.x + rect.size.width),
            y = center.y.coerceIn(rect.position.y, rect.position.y + rect.size.height),
        )
        if ((center - closest).lengthSquared() > ball.radius * ball.radius) return

        // Reflect away from the paddle; the vertical component depends on where it hit.
        val paddleCenterY = rect.position.y + rect.size.height / 2f
        val offset = (center.y - paddleCenterY) / (rect.size.height / 2f)
        val directionX = if (paddle.side == Side.LEFT) 1f else -1f
        val speed = ball.velocity.length()
        ball.velocity = Vec2(directionX, offset * VERTICAL_INFLUENCE).normalized() * speed

        // Nudge the ball outside the paddle so it can't collide again next frame.
        val pushedX = if (paddle.side == Side.LEFT) {
            rect.position.x + rect.size.width + ball.radius
        } else {
            rect.position.x - ball.radius
        }
        ball.position = ball.position.copy(x = pushedX)
    }

    private fun score(rightPlayer: Boolean) {
        if (rightPlayer) {
            rightScore++
            rightBoard?.text = "$rightScore"
        } else {
            leftScore++
            leftBoard?.text = "$leftScore"
        }
        ball?.serve()
    }

    private fun collect(node: Node) {
        when (node) {
            is Paddle -> if (node.side == Side.LEFT) leftPaddle = node else rightPaddle = node
            is Ball -> ball = node
            is ScoreBoard -> if (node.side == Side.LEFT) leftBoard = node else rightBoard = node
        }
        node.children.forEach { collect(it) }
    }

    companion object {
        private const val VERTICAL_INFLUENCE = 0.75f
    }
}

package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.Key
import com.neoutils.core.node.Node
import com.neoutils.core.node.findAll
import com.neoutils.core.node.findFirst

class Pong : Node() {

    private var leftPaddle: Paddle? = null
    private var rightPaddle: Paddle? = null
    private var ball: Ball? = null
    private var leftBoard: ScoreBoard? = null
    private var rightBoard: ScoreBoard? = null

    private var leftScore = 0
    private var rightScore = 0

    override fun onReady() {
        val root = tree?.root ?: return

        val paddles = root.findAll<Paddle>()
        leftPaddle = paddles.firstOrNull { it.side == Side.LEFT }
        rightPaddle = paddles.firstOrNull { it.side == Side.RIGHT }

        ball = root.findFirst<Ball>()

        val boards = root.findAll<ScoreBoard>()
        leftBoard = boards.firstOrNull { it.side == Side.LEFT }
        rightBoard = boards.firstOrNull { it.side == Side.RIGHT }

        applyMode(tree?.args as? GameMode ?: GameMode.PLAYER_VS_PLAYER)
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
        val viewport = Viewport.size
        val ball = ball ?: return

        val center = ball.globalPosition()
        when {
            center.x + ball.radius < 0f -> score(rightPlayer = true)
            center.x - ball.radius > viewport.width -> score(rightPlayer = false)
        }
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
}

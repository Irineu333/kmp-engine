package com.neoutils.example.pong

import com.neoutils.core.input.Key
import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.dsl.add
import com.neoutils.skiko.runSkikoWindow

fun main() = runSkikoWindow(
    title = "pong",
) {
    add<Background>()
    add<Net>()
    add<Paddle> {
        side = Side.LEFT
        upKey = Key.W
        downKey = Key.S
    }
    add<Paddle> {
        side = Side.RIGHT
        upKey = Key.UP
        downKey = Key.DOWN
    }
    add<Ball>()
    add<ScoreBoard> {
        side = Side.LEFT
    }
    add<ScoreBoard> {
        side = Side.RIGHT
    }
    add<Pong>()

    // debug
    add<BoundsOverlay>()
    add<VelocityOverlay>()
}

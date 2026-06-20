package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.dsl.add
import com.neoutils.skiko.runSkikoWindow

fun main() = runSkikoWindow(
    title = "colliding-balls",
) {
    repeat(8) {
        add<Ball>()
    }

    add<BallCollider>()
    add<BoundsOverlay>()
    add<VelocityOverlay> {
        color = Color.YELLOW
    }
}

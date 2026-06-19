package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.dsl.add
import com.neoutils.dsl.launch
import com.neoutils.skiko.SkikoLauncher

private val launcher = SkikoLauncher()

fun main() = launcher.launch(
    title = "colliding-balls",
    width = 800,
    height = 600,
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

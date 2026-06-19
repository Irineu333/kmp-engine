package com.neoutils.example.bouncing

import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.core.graphics.Color
import com.neoutils.dsl.add
import com.neoutils.dsl.launch
import com.neoutils.skiko.SkikoLauncher

private val launcher = SkikoLauncher()

fun main() = launcher.launch(
    title = "bouncing-ball",
) {
    add<Ball> {
        radius = 32f
        color = Color.RED
    }
    add<BoundsOverlay> {
        color = Color.BLUE
    }
    add<VelocityOverlay> {
        color = Color.GREEN
    }
}

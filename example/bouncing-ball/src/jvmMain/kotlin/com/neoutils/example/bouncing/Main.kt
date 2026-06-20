package com.neoutils.example.bouncing

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.dsl.add
import com.neoutils.skiko.runSkikoWindow

fun main() = runSkikoWindow(
    title = "bouncing-ball",
) {
    scene("main") {
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
}

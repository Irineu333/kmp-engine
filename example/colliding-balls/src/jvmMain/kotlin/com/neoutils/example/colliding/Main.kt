package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.Game
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneTree
import com.neoutils.debug.DebugLayer
import com.neoutils.debug.feature.BoundsFeature
import com.neoutils.debug.feature.FpsFeature
import com.neoutils.skiko.SkikoWindow

fun main() {

    val root = Node()

    repeat(8) {
        root.add(Ball())
    }

    root.add(BallCollider())

    root.add(
        DebugLayer().apply {
            add(FpsFeature())
            add(BoundsFeature())
            add(
                VelocityOverlay().apply {
                    color = Color.YELLOW
                }
            )
        }
    )

    val game = Game.ofMain(SceneTree(root))

    SkikoWindow(title = "colliding-balls").run(game)
}

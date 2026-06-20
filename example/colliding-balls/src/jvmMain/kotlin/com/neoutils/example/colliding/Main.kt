package com.neoutils.example.colliding

import com.neoutils.core.graphics.Color
import com.neoutils.core.scene.BoundsOverlay
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.scene.SceneTree
import com.neoutils.skiko.SkikoWindow

fun main() {

    val root = Node()

    repeat(8) {
        root.add(Ball())
    }

    root.add(BallCollider())
    root.add(BoundsOverlay())
    root.add(VelocityOverlay().apply { color = Color.YELLOW })

    val manager = SceneManager.ofMain(SceneTree(root))

    SkikoWindow(title = "colliding-balls").run(manager)
}

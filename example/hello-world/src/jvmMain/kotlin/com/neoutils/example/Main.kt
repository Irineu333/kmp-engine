package com.neoutils.example

import com.neoutils.core.*
import com.neoutils.skiko.SkikoLauncher

fun main() {

    val scene = SceneTree(
        root = Node().apply {
            add(
                CenteredNode2D().apply {
                    add(
                        CenterAlignLabel().apply {
                            text = "Hello, World!"
                        },
                    )
                },
            )
            add(BoundsOverlay())
        },
    )

    SkikoLauncher().launch(
        scene = scene,
        config = LaunchConfig(title = "hello-world"),
    )
}

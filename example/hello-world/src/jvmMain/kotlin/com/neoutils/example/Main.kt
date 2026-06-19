package com.neoutils.example

import com.neoutils.core.BoundsOverlay
import com.neoutils.dsl.add
import com.neoutils.dsl.launch
import com.neoutils.skiko.SkikoLauncher

fun main() = SkikoLauncher().launch(
    title = "hello-world",
) {
    add<CenteredNode2D> {
        add<CenterAlignLabel> {
            text = "Hello, World!"
        }
    }
    add<BoundsOverlay>()
}

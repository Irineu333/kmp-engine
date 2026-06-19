package com.neoutils.example

import com.neoutils.core.scene.Node2D
import com.neoutils.core.math.Vec2

class CenteredNode2D : Node2D() {

    override fun onProcess(delta: Float) {
        val viewport = tree?.size ?: return

        position = Vec2(
            x = viewport.width / 2f,
            y = viewport.height / 2f,
        )
    }
}

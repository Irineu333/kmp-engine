package com.neoutils.example

import com.neoutils.core.Node2D
import com.neoutils.core.Renderer
import com.neoutils.core.Vec2

open class CenteredNode2D : Node2D() {
    override fun onDraw(renderer: Renderer) {
        val viewport = tree?.size ?: return

        position = Vec2(
            x = viewport.width / 2f,
            y = viewport.height / 2f,
        )
    }
}
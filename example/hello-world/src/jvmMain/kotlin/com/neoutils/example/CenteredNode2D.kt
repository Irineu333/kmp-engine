package com.neoutils.example

import com.neoutils.core.graphics.Viewport
import com.neoutils.core.node.Node2D

class CenteredNode2D : Node2D() {

    override fun onProcess(delta: Float) {
        position = Viewport.size.center()
    }
}

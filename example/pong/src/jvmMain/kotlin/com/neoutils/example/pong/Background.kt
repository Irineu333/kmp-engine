package com.neoutils.example.pong

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node

class Background : Node() {

    var color: Color = Color(0.10f, 0.11f, 0.13f)

    override fun onDraw(renderer: Renderer) {
        val viewport = tree?.size ?: return
        renderer.drawRect(Rect(Vec2.ZERO, viewport), color, fill = true)
    }
}

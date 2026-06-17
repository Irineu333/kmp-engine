package com.neoutils.example

import com.neoutils.core.Label
import com.neoutils.core.Renderer
import com.neoutils.core.Vec2

class CenterAlignLabel : Label() {
    override fun onDraw(renderer: Renderer) {
        val textSize = renderer.measureText(text, fontSize)

        position = Vec2(
            x = -textSize.width / 2f,
            y = -textSize.height / 2f,
        )

        super.onDraw(renderer)
    }
}
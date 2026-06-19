package com.neoutils.example

import com.neoutils.core.Label
import com.neoutils.core.Vec2

class CenterAlignLabel : Label() {

    override fun onProcess(delta: Float) {
        val size = getSize()

        position = Vec2(
            x = -size.width / 2f,
            y = -size.height / 2f,
        )
    }
}

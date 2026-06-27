package com.neoutils.example.pong.game

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Label

class ScoreBoard : Label() {

    var side: Side = Side.LEFT

    override fun onReady() {
        text = "0"
        color = Color.WHITE
    }

    override fun onProcess(delta: Float) {
        val viewport = Viewport.size
        val size = getSize()

        val centerX = if (side == Side.LEFT) viewport.width / 4f else viewport.width * 3f / 4f
        position = Vec2(
            x = centerX - size.width / 2f,
            y = TOP_MARGIN,
        )
    }

    companion object {
        private const val TOP_MARGIN = 24f
    }
}

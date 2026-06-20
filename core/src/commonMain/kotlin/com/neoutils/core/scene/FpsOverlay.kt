package com.neoutils.core.scene

import com.neoutils.core.graphics.Color
import com.neoutils.core.math.Vec2
import kotlin.math.roundToInt

class FpsOverlay : Label() {

    var margin: Float = 8f

    private var elapsed = 0f
    private var frames = 0

    override fun onReady() {
        text = "FPS: --"
        fontSize = 18f
        color = Color.GREEN
    }

    override fun onProcess(delta: Float) {
        position = Vec2(margin, margin)

        elapsed += delta
        frames++

        if (elapsed >= UPDATE_INTERVAL) {
            text = "FPS: ${(frames / elapsed).roundToInt()}"
            elapsed = 0f
            frames = 0
        }
    }

    companion object {
        private const val UPDATE_INTERVAL = 0.5f
    }
}

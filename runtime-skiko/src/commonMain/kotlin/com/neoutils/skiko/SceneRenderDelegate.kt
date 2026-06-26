package com.neoutils.skiko

import com.neoutils.core.Engine
import com.neoutils.core.input.InputEvent
import com.neoutils.core.math.Size
import com.neoutils.core.time.FrameClock
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

class SceneRenderDelegate(
    private val engine: Engine,
) : SkikoRenderDelegate {

    private val renderer = SkikoRenderer()

    private val textMeasurer = SkikoTextMeasurer()

    private val clock = FrameClock()

    fun enqueue(event: InputEvent) {
        engine.enqueue(event)
    }

    override fun onRender(
        canvas: Canvas,
        width: Int,
        height: Int,
        nanoTime: Long
    ) {
        val delta = clock.tick(nanoTime)

        canvas.clear(org.jetbrains.skia.Color.WHITE)

        renderer.canvas = canvas

        // Feed the host's per-frame render context to the engine, then run the frame.
        engine.viewport.size = Size(width.toFloat(), height.toFloat())
        engine.viewport.textMeasurer = textMeasurer

        engine.update(delta, renderer)
    }
}

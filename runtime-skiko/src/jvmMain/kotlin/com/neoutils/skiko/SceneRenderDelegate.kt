package com.neoutils.skiko

import com.neoutils.core.time.FrameClock
import com.neoutils.core.scene.SceneTree
import com.neoutils.core.math.Size
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

class SceneRenderDelegate(
    private val scene: SceneTree,
) : SkikoRenderDelegate {

    private val renderer = SkikoRenderer()

    private val textMeasurer = SkikoTextMeasurer()

    private val clock = FrameClock()

    override fun onRender(
        canvas: Canvas,
        width: Int,
        height: Int,
        nanoTime: Long
    ) {
        val delta = clock.tick(nanoTime)

        canvas.clear(org.jetbrains.skia.Color.WHITE)

        renderer.canvas = canvas

        scene.size = Size(width.toFloat(), height.toFloat())
        scene.textMeasurer = textMeasurer

        scene.ready()

        scene.process(delta)

        scene.render(renderer)
    }
}
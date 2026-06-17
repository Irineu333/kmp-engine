package com.neoutils.skiko

import com.neoutils.core.SceneTree
import com.neoutils.core.Size
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

class SceneRenderDelegate(
    private val scene: SceneTree
) : SkikoRenderDelegate {

    private val renderer = SkikoRenderer()

    override fun onRender(
        canvas: Canvas,
        width: Int,
        height: Int,
        nanoTime: Long
    ) {
        canvas.clear(org.jetbrains.skia.Color.WHITE)

        renderer.canvas = canvas

        scene.size = Size(width.toFloat(), height.toFloat())

        scene.render(renderer)
    }
}
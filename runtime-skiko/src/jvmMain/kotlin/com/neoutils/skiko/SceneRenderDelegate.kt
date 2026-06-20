package com.neoutils.skiko

import com.neoutils.core.input.InputEvent
import com.neoutils.core.math.Size
import com.neoutils.core.scene.SceneTree
import com.neoutils.core.time.FrameClock
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate
import java.util.concurrent.ConcurrentLinkedQueue

class SceneRenderDelegate(
    private val scene: SceneTree,
) : SkikoRenderDelegate {

    private val renderer = SkikoRenderer()

    private val textMeasurer = SkikoTextMeasurer()

    private val clock = FrameClock()

    private val inputEvents = ConcurrentLinkedQueue<InputEvent>()

    fun enqueue(event: InputEvent) {
        inputEvents.add(event)
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

        scene.size = Size(width.toFloat(), height.toFloat())
        scene.textMeasurer = textMeasurer

        scene.ready()

        scene.input.clearJustPressed()
        while (true) {
            val event = inputEvents.poll() ?: break
            scene.dispatchInput(event)
        }

        scene.process(delta)

        scene.render(renderer)
    }
}
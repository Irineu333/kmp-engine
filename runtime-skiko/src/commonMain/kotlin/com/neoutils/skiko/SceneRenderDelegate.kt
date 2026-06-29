package com.neoutils.skiko

import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.Input
import com.neoutils.core.input.InputEvent
import com.neoutils.core.math.Size
import com.neoutils.core.scene.Game
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.time.FixedStep
import com.neoutils.core.time.FrameClock
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

class SceneRenderDelegate(
    game: Game,
) : SkikoRenderDelegate {

    private val manager = SceneManager(game)

    private val renderer = SkikoRenderer()

    private val clock = FrameClock()

    private val fixedStep = FixedStep()

    private val inputEvents = InputQueue()

    fun enqueue(event: InputEvent) {
        inputEvents.add(event)
    }

    override fun onRender(
        canvas: Canvas,
        width: Int,
        height: Int,
        nanoTime: Long
    ) {
        // Snapshot the active scene for the whole frame: a scene change during
        // process only swaps manager.current; the new scene enters next frame.
        val scene = manager.current

        val delta = clock.tick(nanoTime)

        canvas.clear(org.jetbrains.skia.Color.WHITE)

        renderer.canvas = canvas

        Viewport.size = Size(width.toFloat(), height.toFloat())

        scene.ready()

        Input.clearJustPressed()
        while (true) {
            val event = inputEvents.poll() ?: break
            scene.dispatchInput(event)
        }

        scene.process(delta)

        // Physics advances in fixed steps, decoupled from the variable frame rate.
        repeat(fixedStep.sample(delta)) {
            scene.physicsProcess(fixedStep.step)
        }

        scene.render(renderer)
    }
}
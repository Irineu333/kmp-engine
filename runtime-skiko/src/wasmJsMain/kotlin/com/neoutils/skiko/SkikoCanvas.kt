package com.neoutils.skiko

import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.Key
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.scene.Game
import com.neoutils.dsl.GameBuilder
import com.neoutils.dsl.game
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import org.jetbrains.skiko.SkikoRenderDelegate
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

/**
 * Browser host that binds the engine to an HTML `<canvas>` via Skiko's wasm SkiaLayer
 * and routes DOM keyboard events into the scene tree. The browser has no OS window, so
 * this is the wasm counterpart of the JVM `SkikoWindow`.
 */
class SkikoCanvas(
    private val canvasElementId: String = "canvas",
) {

    fun run(game: Game) {
        // Wait for the Skia wasm runtime to load before touching any Skia API.
        onWasmReady {
            // The font fetch is async; a coroutine lets the bootstrap read top-to-bottom
            // (load the font, then start rendering) instead of nesting in a callback.
            MainScope().launch {
                val canvas = document.getElementById(canvasElementId) as HTMLCanvasElement
                canvas.setAttribute("tabindex", "0")

                // The browser has no system fonts: install the bundled one before the first
                // frame — otherwise drawText() would have no glyphs and text vanishes.
                installTypeface(loadDefaultFont())

                // Provide the global text-measuring service backed by this runtime's fonts.
                Viewport.textMeasurer = SkikoTextMeasurer()

                val delegate = SceneRenderDelegate(game)
                val skiaLayer = SkiaLayer()

                // SkiaLayer renders on demand; re-request a frame after each one so the
                // engine animates continuously (mirrors the JVM layer's continuous loop).
                val looping = SkikoRenderDelegate { canvas, width, height, nanoTime ->
                    delegate.onRender(canvas, width, height, nanoTime)
                    skiaLayer.needRender()
                }
                skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, looping)

                document.addEventListener("keydown") { event: Event ->
                    val e = event as KeyboardEvent
                    val key = keyOf(e.code)
                    if (key != Key.UNKNOWN) e.preventDefault() // stop arrows/space scrolling the page
                    delegate.enqueue(KeyEvent(key, pressed = true))
                }
                document.addEventListener("keyup") { event: Event ->
                    val e = event as KeyboardEvent
                    delegate.enqueue(KeyEvent(keyOf(e.code), pressed = false))
                }

                skiaLayer.attachTo(canvas)
                skiaLayer.needRender()
            }
        }
    }
}

fun runSkikoCanvas(
    canvasElementId: String = "canvas",
    game: Game,
) {
    SkikoCanvas(canvasElementId).run(game)
}

fun runSkikoCanvas(
    canvasElementId: String = "canvas",
    block: GameBuilder.() -> Unit,
) = runSkikoCanvas(canvasElementId, game(block))

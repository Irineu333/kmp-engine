package com.neoutils.debug

import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Input
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.Key
import com.neoutils.core.scene.Node

/**
 * A toggleable debug capability. Features are plain [Node]s driven by the regular
 * scene-tree walk; [shortcut] flips [enabled], which gates the hooks below.
 */
abstract class DebugFeature(
    private var shortcut: Key? = null,
    var enabled: Boolean = true,
) : Node() {

    final override fun onProcess(delta: Float) {
        shortcut?.let { key ->
            if (Input.isJustPressed(key)) enabled = !enabled
        }
        if (enabled) process(delta)
    }

    final override fun onInput(event: InputEvent) {
        if (enabled) input(event)
    }

    final override fun onDraw(renderer: Renderer) {
        if (enabled) draw(renderer)
    }

    open fun process(delta: Float) {}

    open fun input(event: InputEvent) {}

    open fun draw(renderer: Renderer) {}
}

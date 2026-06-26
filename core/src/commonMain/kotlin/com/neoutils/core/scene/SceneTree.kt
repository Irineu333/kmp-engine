package com.neoutils.core.scene

import com.neoutils.core.Engine
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.InputEvent

/**
 * The single, persistent node graph. It owns a permanent [root]; the active scene is
 * mounted under it (by [SceneManager]) and swapped on scene changes. The tree only
 * walks the graph for each lifecycle phase — input/viewport/scene state live in the
 * [Engine] and its injected collaborators.
 */
class SceneTree {

    val root: Node = Node()

    fun ready(engine: Engine) {
        ready(root, engine)
    }

    private fun ready(node: Node, engine: Engine) {
        if (node.engine == null) {
            node.engine = engine
            node.onReady()
        }
        node.children.forEach {
            ready(it, engine)
        }
    }

    fun process(delta: Float) {
        process(root, delta)
    }

    private fun process(node: Node, delta: Float) {
        node.onProcess(delta)
        node.children.forEach {
            process(it, delta)
        }
    }

    fun dispatchInput(event: InputEvent) {
        dispatchInput(root, event)
    }

    private fun dispatchInput(node: Node, event: InputEvent) {
        node.onInput(event)
        node.children.forEach {
            dispatchInput(it, event)
        }
    }

    fun render(renderer: Renderer) {
        render(root, renderer)
    }

    private fun render(node: Node, renderer: Renderer) {
        node.onDraw(renderer)
        node.children.forEach {
            render(it, renderer)
        }
    }
}

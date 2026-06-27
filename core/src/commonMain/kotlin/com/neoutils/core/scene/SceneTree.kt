package com.neoutils.core.scene

import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Input
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.KeyEvent

class SceneTree(
    val root: Node,
) {

    var manager: SceneManager? = null
        internal set

    var args: Any? = null
        internal set

    fun ready() {
        ready(root)
    }

    private fun ready(node: Node) {
        if (node.tree == null) {
            node.tree = this
            node.onReady()
        }
        node.children.forEach {
            ready(it)
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
        if (event is KeyEvent) Input.update(event)
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

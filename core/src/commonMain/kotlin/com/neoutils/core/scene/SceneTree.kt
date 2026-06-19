package com.neoutils.core.scene

import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.TextMeasurer
import com.neoutils.core.math.Size

class SceneTree(
    val root: Node,
) {

    var size: Size = Size.ZERO

    var textMeasurer: TextMeasurer? = null

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

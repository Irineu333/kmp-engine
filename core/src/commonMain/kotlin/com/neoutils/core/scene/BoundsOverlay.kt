package com.neoutils.core.scene

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer

class BoundsOverlay: Node() {

    var color: Color = Color.RED

    override fun onDraw(renderer: Renderer) {

        val root = tree?.root ?: return

        drawBounds(root, renderer)
    }

    private fun drawBounds(node: Node, renderer: Renderer) {

        if (node is Node2D) {
            node.globalBounds()?.let {
                renderer.drawRect(it, color)
            }
        }

        node.children.forEach {
            drawBounds(it, renderer)
        }
    }
}

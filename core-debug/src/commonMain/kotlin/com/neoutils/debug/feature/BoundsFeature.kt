package com.neoutils.debug.feature

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Key
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.Node2D
import com.neoutils.debug.DebugFeature

/**
 * Walks the whole scene tree and outlines the global bounds of every [Node2D]
 * that reports them. Toggle with [F2][Key.F2].
 */
class BoundsFeature : DebugFeature(shortcut = Key.F2, enabled = false) {

    var color: Color = Color.RED

    override fun draw(renderer: Renderer) {
        drawBounds(engine?.root ?: return, renderer)
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

package com.neoutils.core.scene

import com.neoutils.core.Engine
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.InputEvent

open class Node {

    var engine: Engine? = null
        internal set

    var parent: Node? = null
        private set

    val children: List<Node>
        field = mutableListOf()

    fun add(node: Node) {
        node.parent = this
        children.add(node)
    }

    fun remove(node: Node) {
        if (children.remove(node)) {
            node.parent = null
        }
    }

    open fun onReady() {}

    open fun onProcess(delta: Float) {}

    open fun onInput(event: InputEvent) {}

    open fun onDraw(renderer: Renderer) {}
}

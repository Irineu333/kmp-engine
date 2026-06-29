package com.neoutils.core.node

import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.InputEvent
import com.neoutils.core.scene.SceneTree

open class Node {

    var tree: SceneTree? = null
        internal set

    var parent: Node? = null
        private set

    val children: List<Node>
        field = mutableListOf()

    fun add(node: Node) {
        node.parent = this
        children.add(node)
    }

    open fun onReady() {}

    open fun onProcess(delta: Float) {}

    open fun onPhysicsProcess(delta: Float) {}

    open fun onInput(event: InputEvent) {}

    open fun onDraw(renderer: Renderer) {}
}

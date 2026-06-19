package com.neoutils.core

open class Node {

    var tree: SceneTree? = null

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

    open fun onDraw(renderer: Renderer) {}
}

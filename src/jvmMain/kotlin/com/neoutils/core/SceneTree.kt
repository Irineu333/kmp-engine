package com.neoutils.core

class SceneTree(val root: Node) {

    var size: Size = Size.ZERO

    fun render(renderer: Renderer) {
        render(root, renderer)
    }

    private fun render(node: Node, renderer: Renderer) {
        node.tree = this
        node.onDraw(renderer)
        node.children.forEach {
            render(it, renderer)
        }
    }
}
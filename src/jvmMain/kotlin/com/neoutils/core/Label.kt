package com.neoutils.core

open class Label : Node2D() {

    var text: String = "Label"
    var fontSize: Float = 48f
    var color: Color = Color.BLUE

    override fun onDraw(renderer: Renderer) {
        renderer.drawText(text, globalPosition(), fontSize, color)
    }
}
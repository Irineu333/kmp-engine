package com.neoutils.core.scene

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2

open class Label : Node2D() {

    var text: String = "Label"
    var fontSize: Float = 48f
    var color: Color = Color.BLUE

    fun getSize(): Size {
        return engine?.textMeasurer?.measure(text, fontSize) ?: Size.ZERO
    }

    override fun bounds(): Rect = Rect(Vec2.ZERO, getSize())

    override fun onDraw(renderer: Renderer) {
        renderer.drawText(text, globalPosition(), fontSize, color)
    }
}

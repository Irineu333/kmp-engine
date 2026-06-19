package com.neoutils.core.graphics

import com.neoutils.core.math.Rect
import com.neoutils.core.math.Vec2

interface Renderer {
    fun drawText(
        text: String,
        position: Vec2,
        size: Float,
        color: Color
    )

    fun drawRect(
        rect: Rect,
        color: Color,
        fill: Boolean = false,
    )

    fun drawCircle(
        center: Vec2,
        radius: Float,
        color: Color,
        fill: Boolean = true,
    )
}

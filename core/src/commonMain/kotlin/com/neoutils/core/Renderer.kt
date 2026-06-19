package com.neoutils.core

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

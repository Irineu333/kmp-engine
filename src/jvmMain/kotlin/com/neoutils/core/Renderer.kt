package com.neoutils.core

interface Renderer {
    fun measureText(
        text: String,
        size: Float
    ): Size

    fun drawText(
        text: String,
        position: Vec2,
        size: Float,
        color: Color
    )
}


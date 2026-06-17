package com.neoutils.core

data class Color(
    val r: Float,
    val g: Float,
    val b: Float,
    val a: Float = 1f
) {

    companion object {
        val BLACK = Color(0f, 0f, 0f)
        val WHITE = Color(1f, 1f, 1f)
        val RED = Color(1f, 0f, 0f)
        val GREEN = Color(0f, 1f, 0f)
        val BLUE = Color(0f, 0f, 1f)
        val GRAY = Color(0.5f, 0.5f, 0.5f)
    }
}
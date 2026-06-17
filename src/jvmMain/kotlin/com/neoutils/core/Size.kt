package com.neoutils.core

data class Size(
    val width: Float,
    val height: Float,
) {

    companion object {
        val ZERO: Size = Size(0f, 0f)
    }
}

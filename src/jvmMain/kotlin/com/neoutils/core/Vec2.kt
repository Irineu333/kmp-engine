package com.neoutils.core

data class Vec2(
    val x: Float,
    val y: Float
) {

    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)

    companion object {
        val ZERO: Vec2 = Vec2(0f, 0f)
        val ONE: Vec2 = Vec2(1f, 1f)
    }
}
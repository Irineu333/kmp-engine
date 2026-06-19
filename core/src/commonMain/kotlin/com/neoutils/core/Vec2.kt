package com.neoutils.core

data class Vec2(
    val x: Float,
    val y: Float
) {

    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)

    operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)

    operator fun unaryMinus() = Vec2(-x, -y)

    operator fun times(scalar: Float) = Vec2(x * scalar, y * scalar)

    operator fun times(other: Vec2) = Vec2(x * other.x, y * other.y)

    operator fun div(scalar: Float) = Vec2(x / scalar, y / scalar)

    companion object {
        val ZERO: Vec2 = Vec2(0f, 0f)
        val ONE: Vec2 = Vec2(1f, 1f)
    }
}
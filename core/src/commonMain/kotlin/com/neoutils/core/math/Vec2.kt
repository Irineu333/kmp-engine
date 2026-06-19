package com.neoutils.core.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

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

        fun random(): Vec2 = Vec2(Random.nextFloat(), Random.nextFloat())
        fun fromAngle(radians: Float): Vec2 = Vec2(cos(radians), sin(radians))
    }
}
package com.neoutils.core.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
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

    /** Euclidean length of the vector. */
    fun length(): Float = sqrt(lengthSquared())

    /** Squared length; cheaper than [length] when only comparing magnitudes. */
    fun lengthSquared(): Float = x * x + y * y

    /** Unit vector with the same direction, or [ZERO] if this has no length. */
    fun normalized(): Vec2 {
        val length = length()
        return if (length == 0f) ZERO else this / length
    }

    /** Dot product with [other]. */
    fun dot(other: Vec2): Float = x * other.x + y * other.y

    companion object {
        val ZERO: Vec2 = Vec2(0f, 0f)
        val ONE: Vec2 = Vec2(1f, 1f)

        fun random(): Vec2 = Vec2(Random.nextFloat(), Random.nextFloat())
        fun fromAngle(radians: Float): Vec2 = Vec2(cos(radians), sin(radians))
    }
}
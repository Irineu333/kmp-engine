package com.neoutils.core.math

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Vec2Test {

    @Test
    fun length_isEuclideanNorm() {
        assertEquals(5f, Vec2(3f, 4f).length())
    }

    @Test
    fun lengthSquared_avoidsSqrt() {
        assertEquals(25f, Vec2(3f, 4f).lengthSquared())
    }

    @Test
    fun normalized_hasUnitLength() {
        val unit = Vec2(3f, 4f).normalized()

        assertEquals(0.6f, unit.x)
        assertEquals(0.8f, unit.y)
        assertTrue(kotlin.math.abs(unit.length() - 1f) < 1e-6f)
    }

    @Test
    fun normalized_ofZero_isZero() {
        assertEquals(Vec2.ZERO, Vec2.ZERO.normalized())
    }

    @Test
    fun dot_ofPerpendicularVectors_isZero() {
        assertEquals(0f, Vec2(1f, 0f).dot(Vec2(0f, 1f)))
    }

    @Test
    fun dot_matchesComponentSum() {
        assertEquals(11f, Vec2(1f, 2f).dot(Vec2(3f, 4f)))
    }
}

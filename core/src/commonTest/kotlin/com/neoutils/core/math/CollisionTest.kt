package com.neoutils.core.math

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CollisionTest {

    @Test
    fun circleCircle_overlapping_reportsNormalAndDepth() {
        val hit = intersect(Shape.Circle(10f), Vec2.ZERO, Shape.Circle(10f), Vec2(15f, 0f))

        assertEquals(Vec2(1f, 0f), hit?.normal)
        assertEquals(5f, hit?.depth)
    }

    @Test
    fun circleCircle_apart_isNull() {
        assertNull(intersect(Shape.Circle(10f), Vec2.ZERO, Shape.Circle(10f), Vec2(30f, 0f)))
    }

    @Test
    fun aabbAabb_separatesAlongLeastPenetration() {
        val hit = intersect(Shape.Aabb(Size(10f, 10f)), Vec2.ZERO, Shape.Aabb(Size(10f, 10f)), Vec2(8f, 0f))

        assertEquals(Vec2(1f, 0f), hit?.normal)
        assertEquals(2f, hit?.depth)
    }

    @Test
    fun aabbAabb_apart_isNull() {
        assertNull(intersect(Shape.Aabb(Size(10f, 10f)), Vec2.ZERO, Shape.Aabb(Size(10f, 10f)), Vec2(20f, 0f)))
    }

    @Test
    fun circleAabb_usesClosestPoint() {
        // Circle (A) to the right of the box (B): normal points from A toward B (-x).
        val hit = intersect(Shape.Circle(5f), Vec2(8f, 0f), Shape.Aabb(Size(10f, 10f)), Vec2.ZERO)

        assertEquals(Vec2(-1f, 0f), hit?.normal)
        assertEquals(2f, hit?.depth)
    }

    @Test
    fun aabbCircle_isSymmetricToCircleAabb() {
        // Same geometry, swapped order: normal flips to point from the box (A) toward the circle (B).
        val hit = intersect(Shape.Aabb(Size(10f, 10f)), Vec2.ZERO, Shape.Circle(5f), Vec2(8f, 0f))

        assertEquals(Vec2(1f, 0f), hit?.normal)
        assertEquals(2f, hit?.depth)
    }
}

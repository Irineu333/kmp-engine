package com.neoutils.core.math

import kotlin.test.Test
import kotlin.test.assertEquals

class CenteringTest {

    @Test
    fun center_isHalfExtent() {
        assertEquals(Vec2(50f, 30f), Size(100f, 60f).center())
    }

    @Test
    fun center_ofContent_isCenteredTopLeft() {
        val position = Size(100f, 60f).center(Size(20f, 10f))

        assertEquals(Vec2(40f, 25f), position)
    }

    @Test
    fun center_ofSameSize_isOrigin() {
        val size = Size(100f, 60f)

        assertEquals(Vec2.ZERO, size.center(size))
    }
}

package com.neoutils.core.time

import kotlin.test.Test
import kotlin.test.assertEquals

class FixedStepTest {

    @Test
    fun belowOneStep_releasesNothing() {
        val fixed = FixedStep(step = 1f)

        assertEquals(0, fixed.sample(0.5f))
    }

    @Test
    fun accumulatesAcrossFrames() {
        val fixed = FixedStep(step = 1f)

        assertEquals(0, fixed.sample(0.5f))
        assertEquals(1, fixed.sample(0.5f))
    }

    @Test
    fun clampsStepsPerFrame() {
        val fixed = FixedStep(step = 1f, maxStepsPerFrame = 5)

        assertEquals(5, fixed.sample(10f))
    }

    @Test
    fun dropsBacklogAfterClamp() {
        val fixed = FixedStep(step = 1f, maxStepsPerFrame = 5)

        fixed.sample(10f)
        // The 5 leftover steps were dropped, so a normal frame yields exactly one.
        assertEquals(1, fixed.sample(1f))
    }
}

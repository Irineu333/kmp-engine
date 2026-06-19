package com.neoutils.core

class FrameClock {

    private var lastNanoTime: Long = -1

    fun tick(nanoTime: Long): Float {
        val delta = if (lastNanoTime < 0) 0f else (nanoTime - lastNanoTime) / NANOS_PER_SECOND
        lastNanoTime = nanoTime
        return delta
    }

    private companion object {
        const val NANOS_PER_SECOND = 1_000_000_000f
    }
}

package com.neoutils.core.time

/** Releases accumulated frame time as fixed-size steps, for deterministic physics. */
class FixedStep(
    val step: Float = 1f / 60f,
    val maxStepsPerFrame: Int = 5,
) {

    private var accumulator = 0f

    /** Adds [delta] and returns how many fixed steps to run this frame. */
    fun sample(delta: Float): Int {
        accumulator += delta

        var steps = 0
        while (accumulator >= step && steps < maxStepsPerFrame) {
            accumulator -= step
            steps++
        }

        // Drop any backlog left after the cap to avoid a spiral of death.
        if (accumulator > step) accumulator = 0f

        return steps
    }
}

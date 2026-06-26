package com.neoutils.core.input

/**
 * Buffer of input events captured by the host (off the render thread) and drained by
 * the [Engine][com.neoutils.core.Engine] at the start of each frame. The implementation
 * is platform-specific (e.g. concurrent on the JVM, single-threaded in the browser).
 */
interface InputQueue {
    fun add(event: InputEvent)
    fun poll(): InputEvent?
}

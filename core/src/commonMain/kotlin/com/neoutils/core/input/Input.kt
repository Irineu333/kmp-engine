package com.neoutils.core.input

/** Global keyboard input, Godot-style. */
object Input {

    private val state = InputState()

    fun isPressed(key: Key): Boolean = state.isPressed(key)

    fun isJustPressed(key: Key): Boolean = state.isJustPressed(key)

    fun update(event: KeyEvent) = state.update(event)

    fun clearJustPressed() = state.clearJustPressed()
}

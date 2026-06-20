package com.neoutils.core.input

/**
 * Polling view over keyboard input, derived from the [KeyEvent] stream.
 *
 * Game code only reads ([isPressed]/[isJustPressed]); the runtime owns the
 * mutators ([update]/[clearJustPressed]) and calls them once per frame.
 */
class InputState {

    private val pressed = mutableSetOf<Key>()
    private val justPressed = mutableSetOf<Key>()

    /** True while [key] is held down. */
    fun isPressed(key: Key): Boolean = key in pressed

    /** True only on the frame [key] transitioned from up to down. */
    fun isJustPressed(key: Key): Boolean = key in justPressed

    fun update(event: KeyEvent) {
        if (event.pressed) {
            // add() is false if already present, so auto-repeat doesn't re-arm justPressed.
            if (pressed.add(event.key)) justPressed.add(event.key)
        } else {
            pressed.remove(event.key)
        }
    }

    fun clearJustPressed() {
        justPressed.clear()
    }
}

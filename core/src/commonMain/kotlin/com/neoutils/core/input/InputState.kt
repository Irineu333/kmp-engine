package com.neoutils.core.input

class InputState {

    private val pressed = mutableSetOf<Key>()
    private val justPressed = mutableSetOf<Key>()

    fun isPressed(key: Key): Boolean = key in pressed

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

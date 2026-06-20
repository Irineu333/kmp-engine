package com.neoutils.core.input

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InputStateTest {

    @Test
    fun press_setsPressedAndJustPressed() {
        val state = InputState()

        state.update(KeyEvent(Key.UP, pressed = true))

        assertTrue(state.isPressed(Key.UP))
        assertTrue(state.isJustPressed(Key.UP))
    }

    @Test
    fun clearJustPressed_keepsHeldButClearsTransient() {
        val state = InputState()
        state.update(KeyEvent(Key.UP, pressed = true))

        state.clearJustPressed()

        assertTrue(state.isPressed(Key.UP))
        assertFalse(state.isJustPressed(Key.UP))
    }

    @Test
    fun release_clearsPressed() {
        val state = InputState()
        state.update(KeyEvent(Key.UP, pressed = true))

        state.update(KeyEvent(Key.UP, pressed = false))

        assertFalse(state.isPressed(Key.UP))
    }

    @Test
    fun autoRepeat_doesNotRearmJustPressed() {
        val state = InputState()
        state.update(KeyEvent(Key.A, pressed = true))
        state.clearJustPressed()

        // A second key-down while still held (auto-repeat) must not re-arm justPressed.
        state.update(KeyEvent(Key.A, pressed = true))

        assertTrue(state.isPressed(Key.A))
        assertFalse(state.isJustPressed(Key.A))
    }
}

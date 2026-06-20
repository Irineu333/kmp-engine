package com.neoutils.core.input

sealed interface InputEvent

data class KeyEvent(
    val key: Key,
    val pressed: Boolean,
) : InputEvent

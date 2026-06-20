package com.neoutils.core.scene

/**
 * Builds a fresh [SceneTree] on demand. Used by [SceneManager] to (re)create a
 * scene each time it becomes active, so re-entering a scene starts it clean.
 */
fun interface SceneFactory {
    fun create(): SceneTree
}

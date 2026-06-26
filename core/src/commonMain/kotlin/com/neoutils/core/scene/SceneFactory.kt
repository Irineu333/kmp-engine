package com.neoutils.core.scene

/**
 * Builds a fresh scene root [Node] on demand. Used by [SceneManager] to (re)create a
 * scene each time it becomes active, so re-entering a scene starts it clean. The node
 * is mounted under the single persistent [SceneTree] as its current scene.
 */
fun interface SceneFactory {
    fun create(): Node
}

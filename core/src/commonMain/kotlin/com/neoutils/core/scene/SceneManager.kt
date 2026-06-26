package com.neoutils.core.scene

/**
 * Holds the named [SceneFactory] catalog and tracks the active scene. It mounts the
 * current scene under the single persistent [SceneTree]; a [change] is deferred and
 * applied by [applyPending] at the start of the next frame, so a switch requested
 * during a frame takes effect on the following one.
 */
class SceneManager(
    private val game: Game,
) {

    private val factories: Map<String, SceneFactory> get() = game.scenes

    /** The active scene node, mounted as the child of the tree root. */
    var currentScene: Node? = null
        private set

    /** Payload passed to the current scene via [change]; null for the initial scene. */
    var args: Any? = null
        private set

    private var pending: Pending? = Pending(
        name = factories.keys.firstOrNull() ?: error("No scenes registered"),
        args = null,
    )

    fun change(name: String, args: Any? = null) {
        if (name !in factories) error("Scene not found: $name")
        pending = Pending(name, args)
    }

    /** Mounts a requested scene under [tree]'s root, replacing the previous one. */
    fun applyPending(tree: SceneTree) {
        val next = pending ?: return
        pending = null

        currentScene?.let { tree.root.remove(it) }

        val scene = factories.getValue(next.name).create()
        tree.root.add(scene)
        currentScene = scene
        args = next.args
    }

    private class Pending(val name: String, val args: Any?)
}

package com.neoutils.core.scene

/**
 * Intermediate representation of a game: the immutable recipe of named scenes.
 *
 * This is the public-facing handle a game exposes (e.g. `fun pong(): Game`). The runtime
 * turns it into a [SceneManager] — the mutable, frame-to-frame state — so callers never
 * deal with runtime state directly.
 *
 * [scenes] must be ordered: the first entry is the starting scene. The DSL ([com.neoutils.dsl.game])
 * and [ofMain] both build it from a `linkedMapOf`, preserving registration order.
 */
class Game(
    val scenes: Map<String, SceneFactory>,
) {
    init {
        check(scenes.isNotEmpty()) { "No scenes registered" }
    }

    companion object {
        /** Single-scene game built from a ready tree (no DSL). */
        fun ofMain(tree: SceneTree): Game =
            Game(linkedMapOf("main" to SceneFactory { tree }))
    }
}

package com.neoutils.core.scene

/**
 * Intermediate representation of a game: a platform-agnostic, immutable catalog of named
 * scenes. This is the public hand-off between a game definition (e.g. a `commonMain`
 * `fun pong(): Game`) and a runtime, which assembles an [Engine][com.neoutils.core.Engine]
 * from it. The first registered scene is the initial one by convention.
 */
class Game(val scenes: Map<String, SceneFactory>) {
    companion object {
        fun ofMain(root: Node): Game = Game(mapOf("main" to SceneFactory { root }))
    }
}

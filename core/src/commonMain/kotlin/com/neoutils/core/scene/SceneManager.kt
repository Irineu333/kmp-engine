package com.neoutils.core.scene

class SceneManager(
    private val game: Game,
) {
    var current: SceneTree = build(game.scenes.keys.first())
        private set

    fun change(name: String, args: Any? = null) {
        current = build(name, args)
    }

    private fun build(name: String, args: Any? = null): SceneTree {
        val factory = game.scenes[name] ?: error("Scene not found: $name")
        return factory.create().also {
            it.manager = this
            it.args = args
        }
    }
}

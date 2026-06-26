package com.neoutils.dsl

import com.neoutils.core.scene.Game
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneFactory

fun <T : Node> node(
    factory: () -> T,
    block: T.() -> Unit = {},
): T = factory().apply(block)

fun <T : Node> Node.add(
    factory: () -> T,
    block: T.() -> Unit = {},
): T = node(factory, block).also { add(it) }

fun scene(
    block: Node.() -> Unit,
): Node = Node().apply(block)

class GameBuilder {

    val scenes = linkedMapOf<String, SceneFactory>()

    fun scene(name: String, block: Node.() -> Unit) {
        scenes[name] = SceneFactory { scene(block) }
    }

    fun build(): Game = Game(scenes)
}

fun game(block: GameBuilder.() -> Unit): Game = GameBuilder().apply(block).build()

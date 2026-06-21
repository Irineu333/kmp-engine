package com.neoutils.dsl

import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneFactory
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.scene.SceneTree

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
): SceneTree = SceneTree(root = Node().apply(block))

class ScenesBuilder {

    val factories = linkedMapOf<String, SceneFactory>()

    fun scene(name: String, block: Node.() -> Unit) {
        factories[name] = SceneFactory { scene(block) }
    }
}

fun game(block: ScenesBuilder.() -> Unit): SceneManager =
    SceneManager(ScenesBuilder().apply(block).factories)

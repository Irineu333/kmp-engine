package com.neoutils.dsl

import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneFactory
import com.neoutils.core.scene.SceneTree
import kotlin.reflect.full.createInstance

inline fun <reified T : Node> node(
    block: T.() -> Unit = {}
): T = T::class.createInstance().apply(block)

inline fun <reified T : Node> Node.add(
    block: T.() -> Unit = {}
): T = node<T>(block).also { add(it) }

inline fun scene(
    block: Node.() -> Unit
): SceneTree = SceneTree(root = node(block))

class ScenesBuilder {

    val factories = linkedMapOf<String, SceneFactory>()

    fun scene(name: String, block: Node.() -> Unit) {
        factories[name] = SceneFactory { scene(block) }
    }
}

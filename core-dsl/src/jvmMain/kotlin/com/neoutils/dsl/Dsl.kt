package com.neoutils.dsl

import com.neoutils.core.app.LaunchConfig
import com.neoutils.core.app.Launcher
import com.neoutils.core.scene.Node
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

fun Launcher.launch(
    title: String = "kmp-engine",
    width: Int = 800,
    height: Int = 600,
    block: Node.() -> Unit,
) = launch(
    scene = scene(block),
    config = LaunchConfig(title = title, width = width, height = height),
)

package com.neoutils.dsl

import com.neoutils.core.scene.Node
import kotlin.reflect.full.createInstance

/**
 * JVM-only convenience: instantiate a node by type via reflection, e.g. `add<MyNode>()`.
 * On other targets (wasmJs) use the factory overload in commonMain: `add(::MyNode)`.
 */
inline fun <reified T : Node> node(
    block: T.() -> Unit = {}
): T = T::class.createInstance().apply(block)

inline fun <reified T : Node> Node.add(
    block: T.() -> Unit = {}
): T = node<T>(block).also { add(it) }

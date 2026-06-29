package com.neoutils.core.node

/** This node followed by all its descendants, depth-first. */
fun Node.selfAndDescendants(): Sequence<Node> = sequence {
    yield(this@selfAndDescendants)
    children.forEach { yieldAll(it.selfAndDescendants()) }
}

/** Every node of type [T] in this subtree (self included), depth-first. */
inline fun <reified T> Node.findAll(): List<T> =
    selfAndDescendants().filterIsInstance<T>().toList()

/** First node of type [T] in this subtree (self included), depth-first, or `null`. */
inline fun <reified T> Node.findFirst(): T? =
    selfAndDescendants().filterIsInstance<T>().firstOrNull()

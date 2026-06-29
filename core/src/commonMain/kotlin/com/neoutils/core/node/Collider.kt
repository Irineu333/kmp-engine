package com.neoutils.core.node

import com.neoutils.core.math.Collision
import com.neoutils.core.math.Shape

/**
 * A [Shape] (centered on this node's [globalPosition]) detected by [CollisionWorld].
 * Override [onCollision] to respond; it receives the [other] collider and a
 * [Collision] whose normal points toward it.
 *
 * Both sides are notified, so a response that resolves the pair must be idempotent
 * (gate on the approach sign) to avoid applying it twice.
 */
open class Collider(val shape: Shape) : Node2D() {
    open fun onCollision(other: Collider, hit: Collision) {}
}

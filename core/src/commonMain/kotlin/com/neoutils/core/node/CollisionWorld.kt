package com.neoutils.core.node

import com.neoutils.core.math.intersect

/**
 * Detects overlaps between all [Collider]s during the physics phase and notifies both
 * sides via [Collider.onCollision]; it reports contacts but never resolves them. O(n²).
 */
class CollisionWorld : Node() {

    override fun onPhysicsProcess(delta: Float) {
        val colliders = tree?.root?.findAll<Collider>() ?: return

        for (i in colliders.indices) {
            for (j in i + 1 until colliders.size) {
                val a = colliders[i]
                val b = colliders[j]

                val hit = intersect(a.shape, a.globalPosition(), b.shape, b.globalPosition()) ?: continue

                a.onCollision(b, hit)
                b.onCollision(a, hit.copy(normal = -hit.normal))
            }
        }
    }
}

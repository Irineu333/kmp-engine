package com.neoutils.example.colliding

import com.neoutils.core.node.Node
import com.neoutils.core.node.findAll

class BallCollider : Node() {

    override fun onProcess(delta: Float) {
        val balls = tree?.root?.findAll<Ball>() ?: return

        for (i in balls.indices) {
            for (j in i + 1 until balls.size) {
                resolve(balls[i], balls[j])
            }
        }
    }

    private fun resolve(a: Ball, b: Ball) {
        val between = b.position - a.position
        val distance = between.length()
        val minDistance = a.radius + b.radius

        if (distance == 0f || distance >= minDistance) return

        val normal = between.normalized()

        val overlap = minDistance - distance
        val totalMass = a.mass + b.mass
        a.position -= normal * (overlap * b.mass / totalMass)
        b.position += normal * (overlap * a.mass / totalMass)

        val approach = (a.velocity - b.velocity).dot(normal)
        if (approach <= 0f) return

        val impulse = 2f * approach / (1f / a.mass + 1f / b.mass)
        a.velocity -= normal * (impulse / a.mass)
        b.velocity += normal * (impulse / b.mass)
    }
}

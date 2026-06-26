package com.neoutils.example.colliding

import com.neoutils.core.scene.Node

class BallCollider : Node() {

    override fun onProcess(delta: Float) {
        val balls = collect(engine?.root ?: return)

        for (i in balls.indices) {
            for (j in i + 1 until balls.size) {
                resolve(balls[i], balls[j])
            }
        }
    }

    private fun collect(node: Node): List<Ball> {
        return buildList {
            if (node is Ball) add(node)
            node.children.forEach {
                addAll(collect(it))
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

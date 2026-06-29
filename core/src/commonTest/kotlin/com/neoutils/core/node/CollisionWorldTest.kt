package com.neoutils.core.node

import com.neoutils.core.math.Collision
import com.neoutils.core.math.Shape
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.SceneTree
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollisionWorldTest {

    private fun run(aPos: Vec2, bPos: Vec2): Pair<List<Collision>, List<Collision>> {
        val hitsA = mutableListOf<Collision>()
        val hitsB = mutableListOf<Collision>()

        val a = object : Collider(Shape.Circle(10f)) {
            override fun onCollision(other: Collider, hit: Collision) { hitsA += hit }
        }.apply { position = aPos }
        val b = object : Collider(Shape.Circle(10f)) {
            override fun onCollision(other: Collider, hit: Collision) { hitsB += hit }
        }.apply { position = bPos }

        val root = Node().apply {
            add(CollisionWorld())
            add(a)
            add(b)
        }

        val tree = SceneTree(root)
        tree.ready()                 // wires `tree` into the world so it can walk the root
        tree.physicsProcess(0.016f)

        return hitsA to hitsB
    }

    @Test
    fun overlapping_notifiesBothSidesWithOpposedNormals() {
        val (hitsA, hitsB) = run(Vec2.ZERO, Vec2(15f, 0f))

        assertEquals(1, hitsA.size)
        assertEquals(1, hitsB.size)
        assertEquals(Vec2(1f, 0f), hitsA.single().normal)
        assertEquals(Vec2(-1f, 0f), hitsB.single().normal)
        assertEquals(5f, hitsA.single().depth)
    }

    @Test
    fun apart_notifiesNobody() {
        val (hitsA, hitsB) = run(Vec2.ZERO, Vec2(40f, 0f))

        assertTrue(hitsA.isEmpty() && hitsB.isEmpty())
    }
}

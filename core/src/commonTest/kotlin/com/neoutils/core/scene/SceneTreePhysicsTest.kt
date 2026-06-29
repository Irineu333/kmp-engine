package com.neoutils.core.scene

import com.neoutils.core.node.Node
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneTreePhysicsTest {

    private class Spy(val name: String, val log: MutableList<String>) : Node() {
        override fun onPhysicsProcess(delta: Float) {
            log += name
        }
    }

    @Test
    fun physicsProcess_isDeliveredToEveryNode_depthFirst() {
        val log = mutableListOf<String>()

        val root = Spy("root", log)
        val childA = Spy("a", log)
        val grandChild = Spy("a.1", log)
        val childB = Spy("b", log)

        root.add(childA)
        childA.add(grandChild)
        root.add(childB)

        SceneTree(root).physicsProcess(0.016f)

        assertEquals(listOf("root", "a", "a.1", "b"), log)
    }
}

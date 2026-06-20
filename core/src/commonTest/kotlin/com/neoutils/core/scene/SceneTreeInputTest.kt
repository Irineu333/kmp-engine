package com.neoutils.core.scene

import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.Key
import com.neoutils.core.input.KeyEvent
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneTreeInputTest {

    private class Spy(val name: String, val log: MutableList<String>) : Node() {
        override fun onInput(event: InputEvent) {
            log += name
        }
    }

    @Test
    fun input_isDeliveredToEveryNode_depthFirst() {
        val log = mutableListOf<String>()

        val root = Spy("root", log)
        val childA = Spy("a", log)
        val grandChild = Spy("a.1", log)
        val childB = Spy("b", log)

        root.add(childA)
        childA.add(grandChild)
        root.add(childB)

        val tree = SceneTree(root)
        tree.input(KeyEvent(Key.LEFT, pressed = true))

        assertEquals(listOf("root", "a", "a.1", "b"), log)
    }
}

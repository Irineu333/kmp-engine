package com.neoutils.core.node

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class NodeQueryTest {

    private class A(val id: String) : Node()
    private class B(val id: String) : Node()

    @Test
    fun findAll_collectsMatchingNodes_depthFirst() {
        val root = A("root")
        val childA = A("a")
        val grandChild = A("a.1")
        val childB = B("b")

        root.add(childA)
        childA.add(grandChild)
        root.add(childB)

        val found = root.findAll<A>()

        assertEquals(listOf("root", "a", "a.1"), found.map { it.id })
    }

    @Test
    fun findAll_includesSelf() {
        val root = A("root")

        assertEquals(listOf("root"), root.findAll<A>().map { it.id })
    }

    @Test
    fun findFirst_returnsFirstInDepthFirstOrder() {
        val root = A("root")
        val child = B("b")
        root.add(child)

        assertSame(child, root.findFirst<B>())
    }

    @Test
    fun findFirst_whenAbsent_isNull() {
        assertNull(A("root").findFirst<B>())
    }
}

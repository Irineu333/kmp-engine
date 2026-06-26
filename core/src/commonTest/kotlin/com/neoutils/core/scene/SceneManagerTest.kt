package com.neoutils.core.scene

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertSame

class SceneManagerTest {

    private fun factories(): Map<String, SceneFactory> = linkedMapOf(
        "menu" to SceneFactory { Node() },
        "game" to SceneFactory { Node() },
    )

    @Test
    fun startsOnFirstScene_mountedUnderRoot() {
        val menuRoot = Node()
        val ordered: Map<String, SceneFactory> = linkedMapOf(
            "menu" to SceneFactory { menuRoot },
            "game" to SceneFactory { Node() },
        )
        val manager = SceneManager(Game(ordered))
        val tree = SceneTree()

        manager.applyPending(tree)

        assertSame(menuRoot, manager.currentScene)
        assertSame(menuRoot, tree.root.children.single())
    }

    @Test
    fun change_isDeferred_untilApply() {
        val manager = SceneManager(Game(factories()))
        val tree = SceneTree()
        manager.applyPending(tree)
        val menu = manager.currentScene

        manager.change("game")

        assertSame(menu, manager.currentScene)
    }

    @Test
    fun applyPending_swapsCurrentScene() {
        val manager = SceneManager(Game(factories()))
        val tree = SceneTree()
        manager.applyPending(tree)
        val menu = manager.currentScene

        manager.change("game")
        manager.applyPending(tree)

        assertNotSame(menu, manager.currentScene)
        assertSame(manager.currentScene, tree.root.children.single())
    }

    @Test
    fun change_rebuildsSceneFromScratch() {
        val manager = SceneManager(Game(factories()))
        val tree = SceneTree()
        manager.applyPending(tree)

        manager.change("game")
        manager.applyPending(tree)
        val first = manager.currentScene

        manager.change("game")
        manager.applyPending(tree)
        val second = manager.currentScene

        assertNotSame(first, second)
    }

    @Test
    fun change_withArgs_propagatesPayload() {
        val manager = SceneManager(Game(factories()))
        val tree = SceneTree()
        manager.applyPending(tree)

        manager.change("game", args = "payload")
        manager.applyPending(tree)

        assertEquals("payload", manager.args)
    }

    @Test
    fun change_withoutArgs_resetsArgsToNull() {
        val manager = SceneManager(Game(factories()))
        val tree = SceneTree()
        manager.applyPending(tree)
        manager.change("game", args = "payload")
        manager.applyPending(tree)

        manager.change("menu")
        manager.applyPending(tree)

        assertNull(manager.args)
    }

    @Test
    fun change_toUnknownScene_failsEagerly() {
        val manager = SceneManager(Game(factories()))

        assertFailsWith<IllegalStateException> {
            manager.change("missing")
        }
    }
}

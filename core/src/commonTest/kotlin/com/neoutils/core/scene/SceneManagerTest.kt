package com.neoutils.core.scene

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertSame

class SceneManagerTest {

    private val factories: Map<String, SceneFactory> = linkedMapOf(
        "menu" to SceneFactory { SceneTree(Node()) },
        "game" to SceneFactory { SceneTree(Node()) },
    )

    @Test
    fun startsOnFirstScene_withBackReference() {
        val first = SceneTree(Node())
        val ordered: Map<String, SceneFactory> = linkedMapOf(
            "menu" to SceneFactory { first },
            "game" to SceneFactory { SceneTree(Node()) },
        )

        val manager = SceneManager(Game(ordered))

        assertSame(first, manager.current)
        assertSame(manager, manager.current.manager)
    }

    @Test
    fun change_swapsCurrentScene() {
        val manager = SceneManager(Game(factories))
        val menu = manager.current

        manager.change("game")

        assertNotSame(menu, manager.current)
        assertSame(manager, manager.current.manager)
    }

    @Test
    fun change_rebuildsSceneFromScratch() {
        val manager = SceneManager(Game(factories))

        manager.change("game")
        val first = manager.current
        manager.change("game")
        val second = manager.current

        assertNotSame(first, second)
    }

    @Test
    fun change_withArgs_propagatesPayloadToScene() {
        val manager = SceneManager(Game(factories))

        manager.change("game", args = "payload")

        assertEquals("payload", manager.current.args)
    }

    @Test
    fun change_withoutArgs_leavesArgsNull() {
        val manager = SceneManager(Game(factories))

        manager.change("game")

        assertNull(manager.current.args)
    }

    @Test
    fun changeScene_fromTree_forwardsArgsToManager() {
        val manager = SceneManager(Game(factories))

        manager.current.changeScene("game", args = 42)

        assertEquals(42, manager.current.args)
    }

    @Test
    fun change_toUnknownScene_fails() {
        val manager = SceneManager(Game(factories))

        assertFailsWith<IllegalStateException> {
            manager.change("missing")
        }
    }
}

package com.neoutils.core.scene

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class GameTest {

    @Test
    fun ofMain_registersSingleNamedScene() {
        val tree = SceneTree(Node())

        val game = Game.ofMain(tree)

        assertEquals(setOf("main"), game.scenes.keys)
        assertSame(tree, game.scenes.getValue("main").create())
    }

    @Test
    fun emptyScenes_fails() {
        assertFailsWith<IllegalStateException> {
            Game(emptyMap())
        }
    }
}

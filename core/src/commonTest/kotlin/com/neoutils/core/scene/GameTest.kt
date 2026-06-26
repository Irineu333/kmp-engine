package com.neoutils.core.scene

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class GameTest {

    @Test
    fun ofMain_exposesSingleMainScene() {
        val root = Node()

        val game = Game.ofMain(root)

        assertEquals(listOf("main"), game.scenes.keys.toList())
        assertSame(root, game.scenes.getValue("main").create())
    }

    @Test
    fun scenes_preserveRegistrationOrder() {
        val game = Game(
            linkedMapOf(
                "menu" to SceneFactory { Node() },
                "game" to SceneFactory { Node() },
            )
        )

        assertEquals(listOf("menu", "game"), game.scenes.keys.toList())
    }
}

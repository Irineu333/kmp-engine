package com.neoutils.core

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.InputQueue
import com.neoutils.core.input.InputState
import com.neoutils.core.input.Key
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Game
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneFactory
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.scene.SceneTree
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

private object NoopRenderer : Renderer {
    override fun drawText(text: String, position: Vec2, size: Float, color: Color) {}
    override fun drawRect(rect: Rect, color: Color, fill: Boolean) {}
    override fun drawCircle(center: Vec2, radius: Float, color: Color, fill: Boolean) {}
    override fun drawLine(start: Vec2, end: Vec2, color: Color, width: Float) {}
}

private class FakeQueue : InputQueue {
    private val events = ArrayDeque<InputEvent>()
    override fun add(event: InputEvent) { events.addLast(event) }
    override fun poll(): InputEvent? = events.removeFirstOrNull()
}

class EngineTest {

    private class Recorder(val log: MutableList<String>) : Node() {
        override fun onReady() { log += "ready" }
        override fun onProcess(delta: Float) { log += "process" }
        override fun onInput(event: InputEvent) { log += "input" }
        override fun onDraw(renderer: Renderer) { log += "draw" }
    }

    private fun engineOf(
        vararg scenes: Pair<String, SceneFactory>,
        queue: InputQueue = FakeQueue(),
    ): Engine = Engine(
        tree = SceneTree(),
        scenes = SceneManager(Game(linkedMapOf(*scenes))),
        input = InputState(),
        viewport = Viewport(),
        queue = queue,
    )

    @Test
    fun update_runsLifecycleInOrder_readyInputProcessDraw() {
        val log = mutableListOf<String>()
        val queue = FakeQueue()
        val engine = engineOf(
            "main" to SceneFactory { Node().apply { add(Recorder(log)) } },
            queue = queue,
        )
        queue.add(KeyEvent(Key.LEFT, pressed = true))

        engine.update(delta = 0.016f, renderer = NoopRenderer)

        assertEquals(listOf("ready", "input", "process", "draw"), log)
    }

    @Test
    fun update_mountsFirstScene_andInjectsEngine() {
        val node = Node()
        val engine = engineOf("main" to SceneFactory { node })

        engine.update(0f, NoopRenderer)

        assertSame(engine, node.engine)
        assertSame(node, engine.scenes.currentScene)
    }

    @Test
    fun changeScene_takesEffectOnNextFrame() {
        val menu = Node()
        val game = Node()
        val engine = engineOf(
            "menu" to SceneFactory { menu },
            "game" to SceneFactory { game },
        )

        engine.update(0f, NoopRenderer)               // mounts the first scene (menu)
        assertSame(menu, engine.scenes.currentScene)

        engine.changeScene("game", args = 42)         // requested during this frame
        assertSame(menu, engine.scenes.currentScene)  // still menu until next frame

        engine.update(0f, NoopRenderer)               // applies the deferred change
        assertSame(game, engine.scenes.currentScene)
        assertEquals(42, engine.args)
    }

    @Test
    fun update_drainsQueue_intoInputState() {
        val queue = FakeQueue()
        val engine = engineOf("main" to SceneFactory { Node() }, queue = queue)
        queue.add(KeyEvent(Key.LEFT, pressed = true))

        engine.update(0f, NoopRenderer)

        assertTrue(engine.input.isPressed(Key.LEFT))
        assertTrue(engine.input.isJustPressed(Key.LEFT))
    }
}

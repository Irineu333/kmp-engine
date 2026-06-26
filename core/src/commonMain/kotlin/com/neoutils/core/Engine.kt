package com.neoutils.core

import com.neoutils.core.graphics.Renderer
import com.neoutils.core.graphics.TextMeasurer
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.InputQueue
import com.neoutils.core.input.InputState
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.math.Size
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.scene.SceneTree

/**
 * The motor that orchestrates the engine's injected capabilities. It owns the frame
 * loop and wires the [SceneTree] (node graph), [SceneManager] (active scene), [input]
 * state, [viewport] (render context) and the input [queue] together — each collaborator
 * is single-purpose and unaware of the others. Nodes reach these capabilities through
 * the [Engine] reference injected into them during [SceneTree.ready].
 */
class Engine(
    val tree: SceneTree,
    val scenes: SceneManager,
    val input: InputState,
    val viewport: Viewport,
    private val queue: InputQueue,
) {

    // Convenience accessors for nodes (reached via node.engine).
    val root: Node get() = tree.root
    val size: Size get() = viewport.size
    val textMeasurer: TextMeasurer? get() = viewport.textMeasurer
    val args: Any? get() = scenes.args

    fun changeScene(name: String, args: Any? = null) = scenes.change(name, args)

    fun enqueue(event: InputEvent) = queue.add(event)

    /** Runs one frame: apply a pending scene change, then ready → input → process → render. */
    fun update(delta: Float, renderer: Renderer) {
        // Mount a scene requested last frame, so the new scene is readied before it runs.
        scenes.applyPending(tree)

        tree.ready(this)

        input.clearJustPressed()
        while (true) {
            val event = queue.poll() ?: break
            if (event is KeyEvent) input.update(event)
            tree.dispatchInput(event)
        }

        tree.process(delta)

        tree.render(renderer)
    }
}

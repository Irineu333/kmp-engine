package com.neoutils.skiko

import com.neoutils.core.Engine
import com.neoutils.core.graphics.Viewport
import com.neoutils.core.input.InputState
import com.neoutils.core.scene.Game
import com.neoutils.core.scene.SceneManager
import com.neoutils.core.scene.SceneTree

fun engineOf(game: Game): Engine = Engine(
    tree = SceneTree(),
    scenes = SceneManager(game),
    input = InputState(),
    viewport = Viewport(),
    queue = createInputQueue(),
)

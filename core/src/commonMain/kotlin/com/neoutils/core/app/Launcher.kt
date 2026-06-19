package com.neoutils.core.app

import com.neoutils.core.scene.SceneTree

interface Launcher {
    fun launch(scene: SceneTree, config: LaunchConfig = LaunchConfig())
}

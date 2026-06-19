package com.neoutils.core

interface Launcher {
    fun launch(scene: SceneTree, config: LaunchConfig = LaunchConfig())
}

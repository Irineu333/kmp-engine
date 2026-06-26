package com.neoutils.example.pong.game

import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.Key
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.scene.Node

class ReturnToMenu : Node() {

    override fun onInput(event: InputEvent) {
        if (event is KeyEvent && event.pressed && event.key == Key.ESCAPE) {
            engine?.changeScene("menu")
        }
    }
}

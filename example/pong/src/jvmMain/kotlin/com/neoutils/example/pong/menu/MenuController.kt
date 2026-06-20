package com.neoutils.example.pong.menu

import com.neoutils.core.input.InputEvent
import com.neoutils.core.input.Key
import com.neoutils.core.input.KeyEvent
import com.neoutils.core.scene.Node

class MenuController : Node() {

    override fun onInput(event: InputEvent) {
        if (event is KeyEvent && event.pressed && event.key == Key.ENTER) {
            tree?.changeScene("pong")
        }
    }
}

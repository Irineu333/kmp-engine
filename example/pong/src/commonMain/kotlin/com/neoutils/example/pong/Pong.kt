package com.neoutils.example.pong

import com.neoutils.core.scene.Game
import com.neoutils.debug.debug
import com.neoutils.dsl.add
import com.neoutils.dsl.game
import com.neoutils.example.pong.game.*
import com.neoutils.example.pong.menu.Menu
import com.neoutils.example.pong.menu.MenuHint
import com.neoutils.example.pong.menu.MenuTitle

fun pong(): Game = game {
    scene("menu") {
        add(::Background)
        add(::MenuTitle)
        add(::Menu)
        add(::MenuHint)

        debug()
    }

    scene("pong") {
        add(::Background)
        add(::Net)
        add(::Paddle) {
            side = Side.LEFT
        }
        add(::Paddle) {
            side = Side.RIGHT
        }
        add(::Ball)
        add(::ScoreBoard) {
            side = Side.LEFT
        }
        add(::ScoreBoard) {
            side = Side.RIGHT
        }
        add(::Pong)
        add(::ReturnToMenu)

        debug {
            add(::VelocityOverlay)
        }
    }
}

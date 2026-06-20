package com.neoutils.example.keyboard

import com.neoutils.dsl.add
import com.neoutils.skiko.runSkikoWindow

fun main() = runSkikoWindow(
    title = "keyboard-input",
) {
    add<KeyDisplay>()
}

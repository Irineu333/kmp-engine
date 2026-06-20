package com.neoutils.example.keyboard

import com.neoutils.dsl.add
import com.neoutils.dsl.launch
import com.neoutils.skiko.SkikoLauncher

private val launcher = SkikoLauncher()

fun main() = launcher.launch(
    title = "keyboard-input",
) {
    add<KeyDisplay>()
}

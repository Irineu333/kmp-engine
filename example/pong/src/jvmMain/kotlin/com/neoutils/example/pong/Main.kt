package com.neoutils.example.pong

import com.neoutils.skiko.runSkikoWindow

fun main() = runSkikoWindow(
    title = "pong",
    game = pong(),
)

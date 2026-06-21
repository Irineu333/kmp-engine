package com.neoutils.example.pong

import com.neoutils.skiko.runSkikoCanvas

fun main() = runSkikoCanvas(
    canvasElementId = "SkikoTarget",
    manager = pong(),
)

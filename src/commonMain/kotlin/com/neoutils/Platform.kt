package com.neoutils

expect fun platformName(): String

fun greeting(): String = "Hello, World from ${platformName()}!"

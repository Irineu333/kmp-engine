package com.neoutils

actual fun platformName(): String = "JVM ${System.getProperty("java.version")}"

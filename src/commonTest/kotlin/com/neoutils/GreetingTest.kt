package com.neoutils

import kotlin.test.Test
import kotlin.test.assertTrue

class GreetingTest {

    @Test
    fun greetingContainsPlatformName() {
        assertTrue(greeting().contains(platformName()))
    }
}

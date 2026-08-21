package com.example.appproject

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testGreeting() {
        assertTrue(Greeting().greet().contains("AppProject"), "Check AppProject is mentioned")
    }
}

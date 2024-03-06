package com.example.libsproject

import com.example.libsproject.firebase.platformFirebaseAnalytics
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonGreetingTest {
    @Test
    fun testExample() {
        assertNotNull(Greeting().greet())
    }

    fun testAnalytics() {
        assertNotNull(platformFirebaseAnalytics())
    }
}
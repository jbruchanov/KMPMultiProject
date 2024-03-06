package com.example.appproject

import com.example.libsproject.firebase.platformFirebaseAnalytics
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonGreetingTest {

    @Test
    fun testAnalytics() {
        assertNotNull(platformFirebaseAnalytics())
    }
}
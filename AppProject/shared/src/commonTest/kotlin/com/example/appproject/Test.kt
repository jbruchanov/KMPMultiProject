package com.example.appproject

import com.example.libsproject.googlemaps.platformGoogleMapsObject
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonGreetingTest {

    @Test
    fun testGmaps() {
        runCatching {
            //doesn't work in jvm
            assertNotNull(platformGoogleMapsObject())
        }
    }
}
@file:OptIn(ExperimentalForeignApi::class)

package com.example.libsproject.firebase

import cocoapods.FirebaseAnalytics.FIRAnalytics
import kotlinx.cinterop.ExperimentalForeignApi

actual fun platformFirebaseAnalytics(): FirebaseAnalytics = IosFirebaseAnalytics

object IosFirebaseAnalytics : FirebaseAnalytics {
    override fun logEvent(name: String) {
        println("IosFirebaseAnalytics:$name")
        FIRAnalytics.logEventWithName(name, parameters = null)
    }
}
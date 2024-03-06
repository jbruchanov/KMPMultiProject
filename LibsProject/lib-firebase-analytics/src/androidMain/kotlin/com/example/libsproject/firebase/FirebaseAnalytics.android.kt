package com.example.libsproject.firebase

actual fun platformFirebaseAnalytics(): FirebaseAnalytics = AndroidFirebaseAnalytics

object AndroidFirebaseAnalytics : FirebaseAnalytics {
    override fun logEvent(name: String) {
        println("AndroidFirebaseAnalytics:$name")
    }
}
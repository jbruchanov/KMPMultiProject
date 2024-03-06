package com.example.libsproject.firebase

expect fun platformFirebaseAnalytics(): FirebaseAnalytics

interface FirebaseAnalytics {
    fun logEvent(name: String)
}
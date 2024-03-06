package com.example.appproject

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "AppProject, ${platform.name}!"
    }
}
package com.example.libsproject

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "LibProject, ${platform.name}!"
    }
}
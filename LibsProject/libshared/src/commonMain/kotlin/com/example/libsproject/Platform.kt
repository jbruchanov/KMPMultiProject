package com.example.libsproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

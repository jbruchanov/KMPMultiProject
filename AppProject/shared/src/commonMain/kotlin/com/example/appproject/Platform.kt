package com.example.appproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
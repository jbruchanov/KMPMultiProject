package com.example.appproject

class JvmPlatofrm : Platform {
    override val name: String = "JVM"
}

actual fun getPlatform(): Platform = JvmPlatofrm()

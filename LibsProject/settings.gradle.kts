enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        mavenLocal()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        google()
        mavenCentral()
    }
}

rootProject.name = "LibsProject"
include(":appAndroid")
include(":shared")
include(":lib-google-maps")

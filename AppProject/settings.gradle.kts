enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AppProject"
include(":appAndroid")
include(":shared")


val includeBuildSample = true
if (includeBuildSample) {
    includeBuild("../LibsProject") {
        dependencySubstitution {
            substitute(module("com.jibru.libs:firebase-analytics")).using(project(":lib-firebase-analytics"))
            substitute(module("com.jibru.libs:google-maps")).using(project(":lib-google-maps"))
        }
    }
}
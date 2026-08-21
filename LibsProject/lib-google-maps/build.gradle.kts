import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidKmpLibrary)
    id("maven-publish")
}

kotlin {
    android {
        namespace = "com.example.libsproject.googlemaps"
        compileSdk = 37
        minSdk = 24
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        withHostTest { }
    }
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = libs.versions.ios.deployment.target.get()
        pod("GoogleMaps") {
            version = libs.versions.cocoa.google.maps.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.google.maps.compose)
            implementation(libs.google.maps.compose.utils)
            implementation(libs.google.maps.compose.widgets)
        }
    }
}

group = "com.jibru.libs"
version = "1.0.0"
publishing {
    publications {
        withType(MavenPublication::class.java).configureEach {
            artifactId = artifactId.replace("lib-", "")
        }
    }
}

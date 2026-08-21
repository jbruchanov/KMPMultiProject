import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinComposeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    android {
        namespace = "com.example.libsproject"
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
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = libs.versions.ios.deployment.target.get()
        podfile = project.file("../appIos/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }

        //needed so binaries of this module (incl. test executables) can link GoogleMaps,
        //which comes in transitively through :lib-google-maps
        pod("GoogleMaps") {
            version = libs.versions.cocoa.google.maps.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(project(":lib-google-maps"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

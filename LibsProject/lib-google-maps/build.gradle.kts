plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.jetbrainsComposeCompiler)
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "16.0"
        pod("GoogleMaps") {
            version = "9.1.0"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain {
            dependencies {
                implementation(libs.google.maps.compose)
            }
        }
    }
}

android {
    namespace = "com.example.libsproject.firebase"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
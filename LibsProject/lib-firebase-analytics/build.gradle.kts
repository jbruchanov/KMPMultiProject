plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
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
        pod("FirebaseAnalytics") {
            version = "10.21.0"
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
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
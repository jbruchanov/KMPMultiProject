@file:OptIn(ExperimentalTestApi::class)

package com.example.appproject

import android.os.Build
import androidx.compose.material.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.junit.Test as Junit4Test
import org.junit.jupiter.api.Test as Junit5Test

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ComposableTest {

    @Junit5Test
    fun testJunit5() {
        println(Build.FINGERPRINT)
        runComposeUiTest {
            setContent {
                Text("Test")
            }
        }
    }

    @Junit4Test
    fun testJunit4() {
        println(Build.FINGERPRINT)
        runComposeUiTest {
            setContent {
                Text("Test")
            }
        }
    }
}

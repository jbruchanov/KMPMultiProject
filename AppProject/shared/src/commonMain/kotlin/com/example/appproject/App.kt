package com.example.appproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.jibru.koolbox.compose.layout.FitOrFallbackLayout
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalContentColor provides Color.White,
            LocalTextStyle provides LocalTextStyle.current.copy(fontSize = 28.sp),
        ) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .verticalScroll(rememberScrollState())
                    .safeDrawingPadding()
            ) {
                repeat(10) {
                    FitOrFallbackLayout(
                        modifier = Modifier.fillMaxSize(),
                        default = {
                            Row(modifier = Modifier.height(IntrinsicSize.Max)) {
                                Text("LEFT TEXT 123")
                                Spacer(Modifier.weight(1f))
                                Text("RIGHT TEXT 123")
                            }
                        },
                        fallback = {
                            Column {
                                Text("LEFT TEXT 123", modifier = Modifier.align(Alignment.Start))
                                Text("RIGHT TEXT 123", modifier = Modifier.align(Alignment.End))
                            }
                        }
                    )
                }
            }
        }
    }
}

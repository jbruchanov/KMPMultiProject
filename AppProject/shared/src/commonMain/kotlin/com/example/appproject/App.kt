@file:OptIn(ExperimentalResourceApi::class)

package com.example.appproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeImageBitmap
import appproject.shared.generated.resources.Res
import appproject.shared.generated.resources.london
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getDrawableResourceBytes
import org.jetbrains.compose.resources.rememberResourceEnvironment
import org.jetbrains.skia.Image

@Composable
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            var image by remember { mutableStateOf<Image?>(null) }
            val resEnv = rememberResourceEnvironment()
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                image = Image.makeFromEncoded(getDrawableResourceBytes(resEnv, Res.drawable.london))
            }
            val cropAction = remember<() -> Boolean> {
                {
                    val im = image
                    if (im != null && im.width > 10 && im.height > 10) {
                        val step = 5
                        image = crop(im, step) as Image
                        im.close()
                        true
                    } else {
                        false
                    }
                }
            }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Button(onClick = { cropAction() }, enabled = image != null) {
                        Text("Crop")
                    }

                    Button(onClick = {
                        scope.launch {
                            image = Image.makeFromEncoded(
                                getDrawableResourceBytes(
                                    resEnv,
                                    Res.drawable.london
                                )
                            )
                        }
                    }) { Text("Reset") }
                }

                val im = image
                if (im != null) {
                    val bitmap = remember(im) { im.toComposeImageBitmap() }
                    Image(
                        bitmap = bitmap,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                var test by remember { mutableIntStateOf(0) }
                LaunchedEffect(test) {
                    if (test == 0) return@LaunchedEffect
                    var counter = 0
                    while (cropAction()) {
                        delay(100)
                        println("Crop step:${counter++}")
                    }
                }
                Button(onClick = {
                    test++
                }, enabled = image != null) {
                    Text("MultiCrop")
                }
            }
        }
    }
}
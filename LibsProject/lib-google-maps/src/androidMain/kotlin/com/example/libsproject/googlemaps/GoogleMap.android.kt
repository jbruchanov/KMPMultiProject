package com.example.libsproject.googlemaps

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
actual fun GoogleMap(modifier: Modifier) {
    var viewport by remember { mutableStateOf("") }
    val camera = rememberCameraPositionState()
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { camera.position }
            .collect {
                viewport = it.target.toString()
            }
    }
    Box(modifier = modifier) {
        com.google.maps.android.compose.GoogleMap(
            cameraPositionState = camera,
            modifier = Modifier.matchParentSize()
        )
        Text(viewport, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
    }

}
@file:OptIn(ExperimentalForeignApi::class)

package com.example.libsproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.MapKit.MKMapView

@Composable
actual fun PlatformScreen(modifier: Modifier) {
    UIKitView(
        factory = {
            MKMapView().also {
                it.setDelegate(MKMapViewDelegate())
            }
        },
        onResize = { view, rect -> view.setFrame(rect) },
        modifier = modifier
    )
}
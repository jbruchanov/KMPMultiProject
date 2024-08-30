@file:OptIn(ExperimentalForeignApi::class)

package com.example.libsproject.googlemaps

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMapViewOptions
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSServices
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.darwin.NSObject

private var init = true
fun initMapKey() {
    if (init) {
        init = false
        GMSServices.provideAPIKey("TODO")
    }
}

@Composable
actual fun GoogleMap(modifier: Modifier) {
    val viewportState = remember { mutableStateOf("") }
    val delegate = remember { MapDelegate(viewportState) }
    Box(modifier = modifier) {
        UIKitView(
            factory = {
                GMSMapView(GMSMapViewOptions()).also {
                    it.setDelegate(delegate)
                }
            },
            onResize = { view, rect -> view.setFrame(rect) },
            modifier = Modifier.matchParentSize()
        )
        val viewport by viewportState
        Text(viewport, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
    }

}

internal class MapDelegate(
    private val state: MutableState<String>
) : NSObject(), GMSMapViewDelegateProtocol {

    init {
        initMapKey()
    }

    override fun mapView(mapView: GMSMapView, didChangeCameraPosition: GMSCameraPosition) {
        memScoped {
            val visibleRegion = mapView.projection().visibleRegion().ptr.pointed
            state.value = buildString {
                append("Corner:${visibleRegion.farLeft.latitude}, ${visibleRegion.farLeft.longitude}")
            }
        }
    }
}
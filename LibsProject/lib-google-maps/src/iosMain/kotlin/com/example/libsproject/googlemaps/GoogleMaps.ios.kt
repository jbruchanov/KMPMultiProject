@file:OptIn(ExperimentalForeignApi::class)

package com.example.libsproject.googlemaps

import cocoapods.GoogleMaps.GMSMapViewOptions
import cocoapods.GoogleMaps.GMSServices
import kotlinx.cinterop.ExperimentalForeignApi

actual fun platformGoogleMapsObject(): Any = GMSMapViewOptions()
actual fun platformGoogleMapsVersion(): String = GMSServices.SDKVersion()

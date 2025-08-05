package com.example.libsproject.googlemaps

import com.google.android.gms.maps.GoogleMapOptions

actual fun platformGoogleMapsObject() : Any = GoogleMapOptions()
actual fun platformGoogleMapsVersion(): String = "android"

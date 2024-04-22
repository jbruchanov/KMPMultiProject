package com.example.libsproject

import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKOverlayRenderer
import platform.MapKit.MKOverlayView
import platform.darwin.NSObject

class MKMapViewDelegate : NSObject(), MKMapViewDelegateProtocol {

    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun mapView(mapView: MKMapView, viewForOverlay: MKOverlayProtocol): MKOverlayView {
        return TODO()
    }

    @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun mapView(mapView: MKMapView, rendererForOverlay: MKOverlayProtocol): MKOverlayRenderer {
        return MKOverlayRenderer()
    }
}
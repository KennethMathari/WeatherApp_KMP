package co.ke.weather.multiplatform.utils

import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile

interface GeolocatorProvider {
    val mobile: Geolocator
}

class DefaultGeolocatorProvider : GeolocatorProvider {

    override val mobile: Geolocator = Geolocator.mobile()
}
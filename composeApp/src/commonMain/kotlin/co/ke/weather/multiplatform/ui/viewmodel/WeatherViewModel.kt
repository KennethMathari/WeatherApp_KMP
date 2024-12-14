package co.ke.weather.multiplatform.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weather.multiplatform.data.model.Location
import co.ke.weather.multiplatform.ui.state.WeatherState
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()

    init {
        getWeatherInfo()
    }


    private fun getWeatherInfo() {
        viewModelScope.launch {

            when (val currentUserLocation = Geolocator.mobile().current(Priority.HighAccuracy)) {
                is GeolocatorResult.Error -> when (currentUserLocation) {
                    is GeolocatorResult.NotSupported -> {
                        _weatherState.value = WeatherState(
                            location = Location(
                                longitude = null,
                                latitude = null,
                                errorMessage = "Geolocation is not supported on this device"
                            )
                        )
                    }

                    is GeolocatorResult.NotFound -> {
                        _weatherState.value = WeatherState(
                            location = Location(
                                longitude = null, latitude = null, errorMessage = "Not Found"
                            )
                        )
                    }

                    is GeolocatorResult.PermissionError -> {
                        _weatherState.value = WeatherState(
                            location = Location(
                                longitude = null, latitude = null, errorMessage = "We don't have permission to use the geolocation services."
                            )
                        )
                    }

                    is GeolocatorResult.GeolocationFailed -> {
                        _weatherState.value = WeatherState(
                            location = Location(
                                longitude = null,
                                latitude = null,
                                errorMessage = "Could not track user"
                            )
                        )
                    }

                    else -> println("Error")
                }

                is GeolocatorResult.Success -> {
                    _weatherState.value = WeatherState(
                        location = Location(
                            longitude = currentUserLocation.data.coordinates.longitude,
                            latitude = currentUserLocation.data.coordinates.latitude,
                            errorMessage = null
                        )
                    )
                }
            }
        }
    }
}
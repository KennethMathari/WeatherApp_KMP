package co.ke.weather.multiplatform.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weather.multiplatform.BuildKonfig
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.ui.state.WeatherState
import co.ke.weather.multiplatform.utils.NetworkResult
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()

    private val openWeatherApiKey = BuildKonfig.OPEN_WEATHER_API_KEY


    init {
        fetchUserLocation()
    }


    private fun fetchUserLocation() {
        viewModelScope.launch {

            _weatherState.value = WeatherState(
                isLoading = true, weatherForecastDTO = null, errorMessage = null
            )

            when (val locationResult = Geolocator.mobile().current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    val latitude = locationResult.data.coordinates.latitude
                    val longitude = locationResult.data.coordinates.longitude

                    getWeatherForecast(latitude, longitude)
                }

                is GeolocatorResult.Error -> {
                    val errorMessage = when (locationResult) {
                        is GeolocatorResult.NotSupported -> "Geolocation is not supported on this device."
                        is GeolocatorResult.NotFound -> "Location not found."
                        is GeolocatorResult.PermissionError -> "We don't have permission to use geolocation services."
                        is GeolocatorResult.GeolocationFailed -> "Could not track user location."
                        else -> "An unknown error occurred while fetching location."
                    }

                    _weatherState.value = WeatherState(
                        isLoading = false, weatherForecastDTO = null, errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private fun getWeatherForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            println("API KEEY:${openWeatherApiKey}")
            weatherRepository.getWeatherForecast(
                latitude = latitude.toString(),
                longitude = longitude.toString(),
                apiKey = openWeatherApiKey
            ).collect { result ->

                when (result) {
                    is NetworkResult.ClientError -> {
                        updateErrorMessage("Unable to Get Weather Forecast! Please Try Again.")
                    }

                    is NetworkResult.NetworkError -> {
                        updateErrorMessage("Check Internet Connection")
                    }

                    is NetworkResult.ServerError -> {
                        updateErrorMessage("Oops! Our Server is Down.")
                    }

                    is NetworkResult.Success -> {
                        _weatherState.value = WeatherState(
                            isLoading = false, weatherForecastDTO = result.data, errorMessage = null
                        )
                    }
                }

            }
        }

    }

    private fun updateErrorMessage(errorMessage: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(
                isLoading = false, weatherForecastDTO = null, errorMessage = errorMessage
            )
        }
    }
}
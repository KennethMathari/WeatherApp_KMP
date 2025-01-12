package co.ke.weather.multiplatform.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ke.weather.multiplatform.BuildKonfig
import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.ui.state.WeatherState
import co.ke.weather.multiplatform.utils.DispatcherProvider
import co.ke.weather.multiplatform.utils.NetworkResult
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val geolocator: Geolocator
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> get() = _weatherState.asStateFlow()

    private val openWeatherApiKey = BuildKonfig.OPEN_WEATHER_API_KEY

    init {
        checkApiKey(openWeatherApiKey)
    }

    fun checkApiKey(openWeatherApiKey: String) {
        if (openWeatherApiKey.isEmpty() || openWeatherApiKey == "DEFAULT_API_KEY") {
            updateErrorMessage(errorMessage = "API Key is not set!")
        } else {
            fetchUserLocation()
        }
    }

    fun fetchUserLocation() {
        viewModelScope.launch(dispatcherProvider.main) {
            _weatherState.value = WeatherState(
                isLoading = true, weatherForecast = null, errorMessage = null
            )

            when (val locationResult = geolocator.current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    val latitude = locationResult.data.coordinates.latitude
                    val longitude = locationResult.data.coordinates.longitude

                    getWeatherForecast(latitude, longitude)
                }

                is GeolocatorResult.Error -> {
                    val errorMessage = when (locationResult) {
                        is GeolocatorResult.NotSupported ->
                            "Geolocation is not supported on this device."
                        is GeolocatorResult.NotFound -> "Location not found."
                        is GeolocatorResult.PermissionError ->
                            "We don't have permission to use geolocation services."
                        is GeolocatorResult.GeolocationFailed -> "Could not track user location."
                        else -> "An unknown error occurred while fetching location."
                    }

                    updateErrorMessage(errorMessage)
                }
            }
        }
    }

    fun getWeatherForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch(dispatcherProvider.main) {
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
                            isLoading = false,
                            weatherForecast = filterDailyWeather(result.data),
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun updateErrorMessage(errorMessage: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            _weatherState.value = WeatherState(
                isLoading = false, weatherForecast = null, errorMessage = errorMessage
            )
        }
    }

    fun filterDailyWeather(forecast: WeatherForecastDTO): WeatherForecastDTO {
        val filteredList = forecast.list.filter { weatherItem ->
            weatherItem.dtTxt.contains("12:00:00")
        }

        return forecast.copy(
            list = filteredList
        )
    }
}
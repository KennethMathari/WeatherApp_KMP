package co.ke.weather.multiplatform.ui.state

import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherForecast: WeatherForecastDTO? = null,
    val errorMessage: String? = null
)

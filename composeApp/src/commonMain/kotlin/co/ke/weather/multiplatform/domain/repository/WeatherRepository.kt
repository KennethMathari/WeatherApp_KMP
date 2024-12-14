package co.ke.weather.multiplatform.domain.repository

import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO
import co.ke.weather.multiplatform.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherForecast(
        latitude: String, longitude: String, apiKey: String
    ): Flow<NetworkResult<WeatherForecastDTO>>
}
package co.ke.weather.multiplatform.data.repository

import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.utils.Constants.WEATHER_FORECAST_BASE_URL
import co.ke.weather.multiplatform.utils.DispatcherProvider
import co.ke.weather.multiplatform.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
) : WeatherRepository {

    override fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String
    ): Flow<NetworkResult<WeatherForecastDTO>> {
        return flow {
            try {
                val url =
                    "$WEATHER_FORECAST_BASE_URL/forecast?lat=$latitude&lon=$longitude&appid=$apiKey"

                val response = httpClient.get(url)

                if (response.status.isSuccess()) {
                    val weatherForecastDTO = response.body<WeatherForecastDTO>()
                    emit(NetworkResult.Success(weatherForecastDTO))
                } else {
                    emit(
                        NetworkResult.NetworkError(
                            Throwable("Error fetching weather: ${response.status.description}")
                        )
                    )
                }
            } catch (e: IOException) {
                emit(NetworkResult.NetworkError(e))
            } catch (e: ResponseException) {
                emit(
                    when (e) {
                        is ClientRequestException -> NetworkResult.ClientError(e)
                        is ServerResponseException -> NetworkResult.ServerError(e)
                        else -> NetworkResult.NetworkError(e)
                    }
                )
            } catch (e: Exception) {
                emit(NetworkResult.NetworkError(e))
            }
        }.flowOn(dispatcherProvider.io)
    }
}

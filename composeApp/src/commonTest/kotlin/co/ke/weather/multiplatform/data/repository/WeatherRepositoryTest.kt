import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.utils.NetworkResult
import co.ke.weather.multiplatform.utils.TestData.apiKey
import co.ke.weather.multiplatform.utils.TestData.latitude
import co.ke.weather.multiplatform.utils.TestData.longitude
import co.ke.weather.multiplatform.utils.TestData.weatherForecastDTO
import co.ke.weather.multiplatform.utils.TestData.error
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherRepositoryTest {

    private val weatherRepository = mock<WeatherRepository>()

    @Test
    fun getWeatherForecastReturnsSuccess() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.Success(weatherForecastDTO))

        weatherRepository.getWeatherForecast(latitude, longitude, apiKey).test {
            assertEquals(NetworkResult.Success(weatherForecastDTO), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getWeatherForecastReturnsClientError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.ClientError(error))

        weatherRepository.getWeatherForecast(latitude, longitude, apiKey).test {
            assertThat(awaitItem()).isEqualTo(NetworkResult.ClientError(error))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getWeatherForecastReturnsNetworkError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.NetworkError(error))

        weatherRepository.getWeatherForecast(latitude, longitude, apiKey).test {
            assertThat(awaitItem()).isEqualTo(NetworkResult.NetworkError(error))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getWeatherForecastReturnsServerError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.ServerError(error))

        weatherRepository.getWeatherForecast(latitude, longitude, apiKey).test {
            assertThat(awaitItem()).isEqualTo(NetworkResult.ServerError(error))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
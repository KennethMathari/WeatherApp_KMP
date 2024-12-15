import assertk.assertThat
import assertk.assertions.isEqualTo
import co.ke.weather.multiplatform.data.model.weather.City
import co.ke.weather.multiplatform.data.model.weather.Coord
import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.utils.NetworkResult
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class WeatherRepositoryTest {

    private val weatherRepository = mock<WeatherRepository>()

    private val weatherForecastDTO = WeatherForecastDTO(
        city = City(
            coord = Coord(
                lat = -1.365646, lon = 36.45675
            ),
            country = "Kenya",
            id = 1,
            name = "Kiambu",
            population = 3455566,
            sunrise = 54633636,
            sunset = 456456,
            timezone = 554365354
        ),
        cnt = 3555, cod = "null", list = emptyList(), message = 200
    )

    private val error = Exception("Error")

    private val latitude = "-1.365646"
    private val longitude = "36.45675"
    private val apiKey = "ksnckwnckwnc"

    @Test
    fun getWeatherForecastReturnsSuccess() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.Success(weatherForecastDTO))

        val result = weatherRepository.getWeatherForecast(latitude, longitude, apiKey).first()

        assertThat(result).isEqualTo(NetworkResult.Success(weatherForecastDTO))
    }

    @Test
    fun getWeatherForecastReturnsClientError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.ClientError(error))

        val result = weatherRepository.getWeatherForecast(latitude, longitude, apiKey).first()

        assertThat(result).isEqualTo(NetworkResult.ClientError(error))
    }

    @Test
    fun getWeatherForecastReturnsNetworkError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.NetworkError(error))

        val result = weatherRepository.getWeatherForecast(latitude, longitude, apiKey).first()

        assertThat(result).isEqualTo(NetworkResult.NetworkError(error))
    }

    @Test
    fun getWeatherForecastReturnsServerError() = runTest {
        everySuspend {
            weatherRepository.getWeatherForecast(
                latitude, longitude, apiKey
            )
        } returns flowOf(NetworkResult.ServerError(error))

        val result = weatherRepository.getWeatherForecast(latitude, longitude, apiKey).first()

        assertThat(result).isEqualTo(NetworkResult.ServerError(error))
    }
}
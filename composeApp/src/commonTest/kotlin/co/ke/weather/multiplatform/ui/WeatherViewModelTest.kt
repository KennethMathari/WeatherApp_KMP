package co.ke.weather.multiplatform.ui

import app.cash.turbine.test
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import co.ke.weather.multiplatform.utils.DispatcherProvider
import co.ke.weather.multiplatform.utils.TestData.weatherForecastDTO
import co.ke.weather.multiplatform.utils.TestDispatcherProvider
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.TrackingStatus
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private val weatherRepository = mock<WeatherRepository>()

    private val testDispatcherProvider: DispatcherProvider = TestDispatcherProvider()
    private lateinit var weatherViewModel: WeatherViewModel

    private val fakeGeolocator = object : Geolocator {

        override val trackingStatus: Flow<TrackingStatus>
            get() = emptyFlow()

        override suspend fun current(priority: Priority): GeolocatorResult {
            return GeolocatorResult.NotFound // Simulate not found error
        }

        override suspend fun isAvailable(): Boolean {
            return true
        }

        override fun stopTracking() {
        }

        override fun track(request: LocationRequest): Flow<TrackingStatus> {
            return emptyFlow()
        }
    }

    @BeforeTest
    fun setUp() {
        weatherViewModel =
            WeatherViewModel(weatherRepository, testDispatcherProvider, fakeGeolocator)
    }

    @Test
    fun `checkApiKey should set error message when API key is not set`() = runTest {
        val invalidApiKey = "DEFAULT_API_KEY"

        weatherViewModel.checkApiKey(invalidApiKey)

        weatherViewModel.weatherState.test {
            val state = awaitItem()
            assertEquals("API Key is not set!", state.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchUserLocation should set error message on geolocation error`() = runTest {
        weatherViewModel.fetchUserLocation()

        weatherViewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state.errorMessage!!.contains("Location not found."))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateErrorMessage should update state after invocation`() = runTest {
        weatherViewModel.updateErrorMessage("error")

        weatherViewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state.errorMessage!!.contains("error"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filterDailyWeather returns WeatherForecastDTO`() = run {
        val result = weatherViewModel.filterDailyWeather(weatherForecastDTO)

        assertEquals(weatherForecastDTO, result)
    }
}
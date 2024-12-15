package co.ke.weather.multiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weather.multiplatform.ui.screens.ErrorScreen
import co.ke.weather.multiplatform.ui.screens.LoadingScreen
import co.ke.weather.multiplatform.ui.screens.WeatherScreen
import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    weatherViewModel: WeatherViewModel = koinViewModel()
) {

    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    when {
        weatherState.isLoading -> {
            LoadingScreen()
        }

        weatherState.errorMessage != null -> {
            ErrorScreen(errorMessage = weatherState.errorMessage ?: "Error")
        }

        weatherState.weatherForecast != null -> {
            weatherState.weatherForecast?.let {
                WeatherScreen(weatherForecastDTO = it)
            }
        }

    }
}
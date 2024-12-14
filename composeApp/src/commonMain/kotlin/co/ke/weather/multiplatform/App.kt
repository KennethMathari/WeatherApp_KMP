package co.ke.weather.multiplatform

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    weatherViewModel: WeatherViewModel = koinViewModel()
) {

    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    MaterialTheme {

        Column {
            Text("Latitude: ${weatherState.location?.latitude}")
            Text("Longitude: ${weatherState.location?.longitude}")
        }

    }
}
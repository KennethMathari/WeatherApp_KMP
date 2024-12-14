package co.ke.weather.multiplatform.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO

@Composable
fun WeatherScreen(
    weatherForecastDTO: WeatherForecastDTO,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text("City: ${weatherForecastDTO.city}")
    }

}
package co.ke.weather.multiplatform.utils

import androidx.compose.ui.graphics.Color
import co.ke.weather.multiplatform.ui.theme.Cloudy
import co.ke.weather.multiplatform.ui.theme.Rainy
import co.ke.weather.multiplatform.ui.theme.Sunny
import org.jetbrains.compose.resources.DrawableResource
import weatherapp.composeapp.generated.resources.Cloudy
import weatherapp.composeapp.generated.resources.Rainy
import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.Sunny
import weatherapp.composeapp.generated.resources.cloudy_icon
import weatherapp.composeapp.generated.resources.rain_icon
import weatherapp.composeapp.generated.resources.sunicon

sealed class WeatherType(
    val weatherDesc: String,
    val iconRes: DrawableResource,
    val imageRes: DrawableResource,
    val color: Color
) {
    data object Clear : WeatherType(
        weatherDesc = "Sunny",
        iconRes = Res.drawable.sunicon,
        imageRes = Res.drawable.Sunny,
        color = Sunny
    )

    data object Clouds : WeatherType(
        weatherDesc = "Cloudy",
        iconRes = Res.drawable.cloudy_icon,
        imageRes = Res.drawable.Cloudy,
        color = Cloudy
    )

    data object Rain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = Res.drawable.rain_icon,
        imageRes = Res.drawable.Rainy,
        color = Rainy
    )

    data object Thunderstorm : WeatherType(
        weatherDesc = "Thunderstorm",
        iconRes = Res.drawable.rain_icon,
        imageRes = Res.drawable.Rainy,
        color = Rainy
    )

    data object Drizzle : WeatherType(
        weatherDesc = "Drizzle",
        iconRes = Res.drawable.rain_icon,
        imageRes = Res.drawable.Rainy,
        color = Rainy
    )

    companion object {
        fun getWeatherType(code: Int): WeatherType {
            return when (code) {
                800 -> Clear
                in 801..804 -> Clouds
                in 500..531 -> Rain
                in 200..232 -> Thunderstorm
                in 300..321 -> Drizzle
                else -> {
                    Clouds
                }
            }
        }
    }
}
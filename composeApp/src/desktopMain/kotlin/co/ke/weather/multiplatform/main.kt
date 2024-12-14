package co.ke.weather.multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.ke.weather.multiplatform.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Weather App",
        ) {
            App()
        }
    }
}
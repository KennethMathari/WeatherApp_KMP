package co.ke.weather.multiplatform

import androidx.compose.ui.window.ComposeUIViewController
import co.ke.weather.multiplatform.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
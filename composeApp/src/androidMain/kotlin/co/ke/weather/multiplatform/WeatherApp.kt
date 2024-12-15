package co.ke.weather.multiplatform

import android.app.Application
import co.ke.weather.multiplatform.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WeatherApp)
        }
    }
}
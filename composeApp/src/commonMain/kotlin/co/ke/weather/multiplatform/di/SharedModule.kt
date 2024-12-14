package co.ke.weather.multiplatform.di

import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {

    single<CoroutineDispatcher> {
        Dispatchers.IO
    }

    viewModelOf(::WeatherViewModel)

}
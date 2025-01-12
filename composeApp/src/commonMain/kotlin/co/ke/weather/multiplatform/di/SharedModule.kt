package co.ke.weather.multiplatform.di

import co.ke.weather.multiplatform.data.repository.WeatherRepositoryImpl
import co.ke.weather.multiplatform.domain.repository.WeatherRepository
import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import co.ke.weather.multiplatform.utils.DefaultDispatcherProvider
import co.ke.weather.multiplatform.utils.DispatcherProvider
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {

    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            httpClient = get(), dispatcherProvider = get()
        )
    }

    single<Geolocator> {
        Geolocator.mobile()
    }

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
                )
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
        }
    }

    viewModelOf(::WeatherViewModel)
}
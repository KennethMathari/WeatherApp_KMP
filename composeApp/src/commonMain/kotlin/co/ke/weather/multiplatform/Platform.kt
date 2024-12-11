package co.ke.weather.multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
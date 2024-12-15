package co.ke.weather.multiplatform.utils

import kotlin.math.roundToInt

fun Double.toCelsius(): String {
    val celsius = (this - 273.15).roundToInt()
    return "$celsius°"
}
package co.ke.weather.multiplatform.utils

import kotlinx.datetime.LocalDateTime

fun String.toDayOfWeek(): String {
    // Parse the input date string to LocalDateTime
    val inputFormatter = "yyyy-MM-dd HH:mm:ss"
    val localDateTime = LocalDateTime.parse(this.replace(" ", "T")) // Ensures compatibility

    val dayOfWeek = localDateTime.dayOfWeek

    return dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}
package co.ke.weather.multiplatform.data.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastDTO(
    @SerialName("city")
    val city: City,
    @SerialName("cnt")
    val cnt: Int,
    @SerialName("cod")
    val cod: String,
    @SerialName("list")
    val list: List<Item0>,
    @SerialName("message")
    val message: Int
)
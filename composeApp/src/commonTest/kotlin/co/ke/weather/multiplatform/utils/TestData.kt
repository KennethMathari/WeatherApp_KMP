package co.ke.weather.multiplatform.utils

import co.ke.weather.multiplatform.data.model.weather.City
import co.ke.weather.multiplatform.data.model.weather.Coord
import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO

object TestData {

    val weatherForecastDTO = WeatherForecastDTO(
        city = City(
            coord = Coord(
                lat = -1.365646, lon = 36.45675
            ),
            country = "Kenya",
            id = 1,
            name = "Kiambu",
            population = 3455566,
            sunrise = 54633636,
            sunset = 456456,
            timezone = 554365354
        ),
            cnt = 3555, cod = "null", list = emptyList(), message = 200
    )

    val error = Exception("Error")

    val latitude = "-1.365646"
    val longitude = "36.45675"
    val apiKey = "ksnckwnckwnc"
}
package co.ke.weather.multiplatform.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weather.multiplatform.data.model.weather.Item0
import co.ke.weather.multiplatform.data.model.weather.WeatherForecastDTO
import co.ke.weather.multiplatform.utils.WeatherType
import co.ke.weather.multiplatform.utils.WeatherType.Companion.getWeatherType
import co.ke.weather.multiplatform.utils.toCelsius
import co.ke.weather.multiplatform.utils.toDayOfWeek
import org.jetbrains.compose.resources.painterResource

@Composable
fun WeatherScreen(
    weatherForecastDTO: WeatherForecastDTO, modifier: Modifier = Modifier
) {

    val backgroundImageRes = getWeatherType(weatherForecastDTO.list[0].weather[0].id).imageRes
    var locationInput by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(backgroundImageRes),
            contentDescription = "Weather Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f))
        ) {
            Text(
                text = "5 Day Forecast",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(15.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = weatherForecastDTO.city.name,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }

                OutlinedTextField(
                    value = locationInput,
                    onValueChange = { locationInput = it },
                    textStyle = TextStyle(color = Color.White),
                    placeholder = {
                        Text(text = "Search City..", color = Color.White)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                    ),
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "search",
                            tint = Color.White
                        )
                    },
                    modifier = Modifier.weight(2f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    )
                )
            }

            Divider(
                color = Color.White, modifier = Modifier.padding(vertical = 10.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(5.dp)
            ) {
                items(weatherForecastDTO.list) { weather ->
                    val weatherType = getWeatherType(weather.weather[0].id)

                    WeatherForecastCard(weather, weatherType)
                }
            }
        }
    }
}

@Composable
fun WeatherForecastCard(
    weather: Item0, weatherType: WeatherType, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 8.dp).fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Text(
                text = weather.dtTxt.toDayOfWeek(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(weatherType.iconRes),
                    contentDescription = weatherType.weatherDesc,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = weather.main.feelsLike.toCelsius(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
            }
        }
    }
}


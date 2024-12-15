package co.ke.weather.multiplatform.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ke.weather.multiplatform.BuildKonfig
import co.ke.weather.multiplatform.ui.viewmodel.WeatherViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    weatherViewModel: WeatherViewModel = koinViewModel()
) {
    val openWeatherApiKey = BuildKonfig.OPEN_WEATHER_API_KEY
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.align(Alignment.Center).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Error Icon",
                tint = Color.Red,
                modifier = Modifier.size(80.dp).padding(bottom = 16.dp)
            )

            Text(
                text = errorMessage, color = Color.Red,
                style = TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.height(24.dp))

            Button(
                onClick = { weatherViewModel.checkApiKey(openWeatherApiKey) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Try Again", color = Color.Black, style = MaterialTheme.typography.button
                )
            }
        }
    }
}

# Compose Multiplatform Weather App | Android | iOS
WeatherApp is a Kotlin Multiplatform (KMP) project that provides a **5-day weather forecast** based on the user's location. Built using **Jetpack Compose Multiplatform**, the app delivers a seamless UI experience on both **Android** and **iOS** platforms.

## Installation Instructions
1. Clone the repository:
> git clone <https://github.com/KennethMathari/WeatherApp_KMP>
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Add your `OPEN_WEATHER_API_KEY` from [OpenWeatherMap](https://openweathermap.org/) to your `local.properties` file
5. Build and run the app on an emulator or physical device in Android & iOS.

## Features
- **5-Day Weather Forecast**: View weather conditions for the next 5 days based on user's current location.
- **Dynamic Backgrounds**: The app updates the background image dynamically based on the weather conditions:
  - **Sunny**: Clear skies with a bright and cheerful background.
  - **Cloudy**: Calm clouds with a soft-toned background.
  - **Rainy**: A wet and moody rain-themed background.
- **Cross-Platform**: Runs natively on Android and iOS devices using Kotlin Multiplatform.
- **Location-Based Forecast**: Automatically fetches the user's location to show accurate weather data.

## API
> 5 Day Weather Forecast API: <https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}>

## Libraries, Plugins & Tools
- <b>Jetpack Compose </b>: For building the UI in a declarative manner.
- <b> Github Actions </b>: Automate CI/CD workflows and manages pipelines.
- <b>Koin </b>: For dependency injection to manage dependencies efficiently.
- <b>Ktor </b>: For network operations to fetch data from the API.
- <b>Kotlinx Serialization </b>: Facilitates data serialization and deserialization in a format-agnostic way.
- <b>KtLint</b>: creates convenient tasks in your Gradle project that run ktlint checks or do code auto format.
- <b>Mokkery </b>: For mocking dependencies in tests.
- <b>Turbine </b>: Specialized library for testing kotlinx.coroutines Flow.
- <b>Build Konfig </b> : BuildConfig for Kotlin Multiplatform Project.
- <b>Compass </b> : Kotlin Multiplatform library location toolkit for geocoding and geolocation
- <b>Assertk </b> : assertions for kotlin tests

Other dependencies are listed in the build.gradle files.

## Android Weather App Screenshots
> ![Android](https://github.com/user-attachments/assets/c7f6b667-7ecf-41b3-a459-210c137477a2)

## iOS Weather App Screenshots
> ![iOS](https://github.com/user-attachments/assets/e2019086-147d-4849-a627-313b6d0144bb)

## Artifacts
The app artifacts(Android & iOS) can be found from the latest successful action on the [GitHub Actions](https://github.com/KennethMathari/WeatherApp_KMP/actions) tab


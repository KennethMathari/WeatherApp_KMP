package co.ke.weather.multiplatform.utils

sealed interface NetworkResult<out D> {
    data class Success<out D>(val data: D) : NetworkResult<D>

    data class NetworkError(val error: Throwable) : NetworkResult<Nothing>

    data class ClientError(val error: Throwable) : NetworkResult<Nothing>

    data class ServerError(val error: Throwable) : NetworkResult<Nothing>
}
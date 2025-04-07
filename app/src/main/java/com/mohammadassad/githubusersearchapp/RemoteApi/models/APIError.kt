package com.mohammadassad.githubusersearchapp.remoteapi.models

sealed class APIError : Exception() {
    object RateLimitExceeded : APIError()
    data class HttpError(val statusCode: Int, val description: String) : APIError()
    data class DecodingError(val error: Exception, val description: String) : APIError()
    data class UnknownError(val error: Exception) : APIError()

    override val message: String
        get() = when (this) {
            is RateLimitExceeded -> "Rate limit exceeded. Please try again later."
            is HttpError -> "API error with status code $statusCode: $description"
            is DecodingError -> "Decoding error: $description"
            is UnknownError -> "Unknown error: ${error.message}"
        }
} 
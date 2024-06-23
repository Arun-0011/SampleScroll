package com.samplescroll.util

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String) : ApiResponse<T>()
    data class Loading<T>(val isLoading: Boolean) : ApiResponse<T>()
}
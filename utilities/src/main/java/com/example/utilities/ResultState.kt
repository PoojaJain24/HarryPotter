package com.example.utilities

sealed class ResultState<out T> {
    class Loading<T> : ResultState<T>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: Any) : ResultState<Nothing>()
}
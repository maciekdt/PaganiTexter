package com.example.loginappexample.service



sealed class RequestResult<out T : Any, out E : Any> {
    data class Success<out T : Any>(val data: T) : RequestResult<T, Nothing>()
    data class Error<out E : Any>(val errorData: E) : RequestResult<Nothing, E>()
}

package com.example.loginappexample.ui.register

data class RegisterResult(
    val error: Int? = null,
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val success: Int? = null
)

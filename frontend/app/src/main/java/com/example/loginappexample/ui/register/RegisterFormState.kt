package com.example.loginappexample.ui.register

data class RegisterFormState(
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val repeatPasswordError: Int? = null,
    val acceptPoliticError: Int? = null,
    val isDataValid: Boolean = false
)
package com.example.loginappexample.ui.register

data class RegisterFormStateData(
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val acceptPolitic: Boolean
)

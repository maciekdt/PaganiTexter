package com.example.loginappexample.ui.login

/**
 * Authentication result : success (DbUser details) or error message.
 */
data class LoginResult(
    val success: Int? = null,
    val error: Int? = null
)
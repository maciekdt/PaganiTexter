package com.example.loginappexample.data.model
import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val name:String,
    val password:String,
    val email:String
)

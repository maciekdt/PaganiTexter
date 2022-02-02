package com.example.loginappexample.data.model
import kotlinx.serialization.*


@Serializable
data class LoggedInUser(
    val token: String,
    val userId: String
){
    override fun toString(): String {
        return token
    }
}
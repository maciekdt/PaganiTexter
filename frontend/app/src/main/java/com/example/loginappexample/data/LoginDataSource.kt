package com.example.loginappexample.data

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.service.BasicUsersData
import com.example.loginappexample.service.LoginService

class LoginDataSource {
    private val service = LoginService()

    suspend fun login(name: String, password: String): LoggedInUser {
        return service.sendLoginRequest(name, password)
    }

    suspend fun checkToken(token: String): Boolean{
        return service.sendCheckTokenRequest(token)
    }

    fun logout() {
        // TODO: revoke authentication
    }

}
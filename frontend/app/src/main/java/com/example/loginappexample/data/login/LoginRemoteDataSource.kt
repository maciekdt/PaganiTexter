package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.service.LoginService

class LoginRemoteDataSource{
    private val service = LoginService()

    suspend fun login(name: String, password: String): LoggedInUser {
        return service.sendLoginRequest(name, password)
    }

    suspend fun checkToken(token: String): Boolean{
        return service.sendCheckTokenRequest(token)
    }
}
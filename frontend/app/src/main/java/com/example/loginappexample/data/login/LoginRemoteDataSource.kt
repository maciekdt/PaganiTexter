package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.data.model.LoginData
import com.example.loginappexample.service.LoginService

class LoginRemoteDataSource : ILoginRemoteDataSource{
    private val service = LoginService()

    override suspend fun login(loginData:LoginData): LoggedInUser {
        return service.sendLoginRequest(loginData.username, loginData.password)
    }

    override suspend fun checkToken(token: String): Boolean{
        return service.sendCheckTokenRequest(token)
    }
}
package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.data.model.LoginData

interface ILoginRemoteDataSource {
    suspend fun login(loginData: LoginData): LoggedInUser
    suspend fun checkToken(token: String): Boolean
}
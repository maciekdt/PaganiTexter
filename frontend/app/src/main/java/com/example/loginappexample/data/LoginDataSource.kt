package com.example.loginappexample.data

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.service.BasicUsersData
import com.example.loginappexample.service.LoginService

class LoginDataSource {
    private val service = LoginService()

    suspend fun login(name: String, password: String): BasicUsersData {
        return service.sendLoginRequest(name, password)
    }

    fun logout() {
        // TODO: revoke authentication
    }

}
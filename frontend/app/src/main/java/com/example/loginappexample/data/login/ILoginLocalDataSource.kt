package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser

interface ILoginLocalDataSource {
    fun login(): LoggedInUser?
    fun saveUser(user: LoggedInUser)
}
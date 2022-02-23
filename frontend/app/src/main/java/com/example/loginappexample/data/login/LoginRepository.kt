package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser

class LoginRepository(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource){

    private var user: LoggedInUser? = null


    suspend fun login(username: String, password: String, rememberMe: Boolean): LoggedInUser {
        val loggedInUser: LoggedInUser = remoteDataSource.login(username, password)
        setLoggedInUser(loggedInUser, rememberMe)
        return  loggedInUser
    }

    suspend fun loginByCache(): Boolean{
        val loggedInUser: LoggedInUser = localDataSource.login() ?: return false
        return remoteDataSource.checkToken(loggedInUser.token)
    }

    fun getLoggedInUser(): LoggedInUser? {return  user}

    private fun setLoggedInUser(loggedInUser: LoggedInUser, rememberMe: Boolean) {
        this.user = loggedInUser
        if(rememberMe) localDataSource.saveUser(loggedInUser)
    }
}
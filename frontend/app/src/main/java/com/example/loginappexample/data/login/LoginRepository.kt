package com.example.loginappexample.data.login

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.data.model.LoginData

class LoginRepository(
    private val remoteDataSource: ILoginRemoteDataSource,
    private val localDataSource: ILoginLocalDataSource){

    private var user: LoggedInUser? = null

    suspend fun loginByRemoteDataSource(loginData: LoginData, rememberMe: Boolean): LoggedInUser {
        val loggedInUser: LoggedInUser = remoteDataSource.login(loginData)
        this.user = loggedInUser
        if(rememberMe) localDataSource.saveUser(loggedInUser)
        return loggedInUser
    }

    suspend fun loginByLocalDataSource(): Boolean{
        val loggedInUser: LoggedInUser = localDataSource.login() ?: return false
        if(remoteDataSource.checkToken(loggedInUser.token)){
            this.user = loggedInUser
            return true
        }
        return false
    }

    fun getLoggedInUser(): LoggedInUser? {return  user}
}
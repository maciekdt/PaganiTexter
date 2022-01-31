package com.example.loginappexample.data

import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.service.BasicUsersData

/**
 * Class that requests authentication and DbUser information from the remote data source and
 * maintains an in-memory cache of login status and DbUser credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    private var user: LoggedInUser? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If DbUser credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): BasicUsersData {
        return dataSource.login(username, password)

    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        this.user = loggedInUser
        // If DbUser credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
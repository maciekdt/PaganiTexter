package com.example.loginappexample.data

import com.example.loginappexample.data.model.LoggedInUser
import android.content.*
import android.util.Log
import com.example.loginappexample.application.GlobalApplication
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.DecodeSequenceMode
import java.io.File
import java.security.AccessController.getContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException

class LoginRepository(private val dataSource: LoginDataSource) {

    private var user: LoggedInUser? = null
    private val loggedInUserCacheFileName: String = "logged-in-user-cache"

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String, rememberMe: Boolean): LoggedInUser {
        val loggedInUser: LoggedInUser = dataSource.login(username, password)
        setLoggedInUser(loggedInUser, rememberMe)
        return  loggedInUser
    }

    suspend fun loginByCache(): Boolean{

        val loggedInUser: LoggedInUser = getLoggedInUserFromCache() ?: return false
        return dataSource.checkToken(loggedInUser.token)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser, rememberMe: Boolean) {
        this.user = loggedInUser
        if(rememberMe) saveLoggedInUserInCache(loggedInUser)
    }

    private fun saveLoggedInUserInCache(loggedInUser: LoggedInUser){
        try {
            val context = GlobalApplication.getAppContext()
            val file = File(context.filesDir, loggedInUserCacheFileName)
            file.writeText(Json.encodeToString(loggedInUser))
        }
        catch(e:IOException){
            e.printStackTrace()
        }
        catch(e:NullPointerException){
            e.printStackTrace()
        }
    }

    private fun getLoggedInUserFromCache(): LoggedInUser?{
        return try{
            val context = GlobalApplication.getAppContext()
            val file = File(context.filesDir, loggedInUserCacheFileName)
            Json.decodeFromString<LoggedInUser>(file.readText())
        } catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}
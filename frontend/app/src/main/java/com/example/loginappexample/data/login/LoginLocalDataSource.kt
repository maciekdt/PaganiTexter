package com.example.loginappexample.data.login

import com.example.loginappexample.application.GlobalApplication
import com.example.loginappexample.data.model.LoggedInUser
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException

class LoginLocalDataSource : ILoginLocalDataSource{
    private val loggedInUserCacheFileName: String = "logged-in-user-cache"

    override fun login(): LoggedInUser?{
        return try{
            val context = GlobalApplication.getAppContext()
            val file = File(context.filesDir, loggedInUserCacheFileName)
            Json.decodeFromString<LoggedInUser>(file.readText())
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override fun saveUser(user: LoggedInUser){
        try {
            val context = GlobalApplication.getAppContext()
            val file = File(context.filesDir, loggedInUserCacheFileName)
            file.writeText(Json.encodeToString(user))
        }
        catch(e: IOException){
            e.printStackTrace()
        }
        catch(e: NullPointerException){
            e.printStackTrace()
        }
    }
}
package com.example.loginappexample.service

import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.loginappexample.data.model.LoggedInUser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class LoginService : Service() {

    suspend fun sendLoginRequest(name:String, password:String): LoggedInUser{
        val route = "/auth/token"
        val response:HttpResponse = client.get(subdomain + route){
            headers {
                append(HttpHeaders.Authorization, encodeAuthHeader(name, password))
            }
        }
        if(response.status.value == 200) {
            val data:LoggedInUser = response.receive()
            Log.i("MyLogService", "Response body : $data")
            return data
        }
        throw getResponseException(response)
    }


    suspend fun sendCheckTokenRequest(token: String): Boolean{
        val route = "/auth/checkToken"
        val response:HttpResponse = client.get(subdomain + route){
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
        if(response.status.value == 200){
            Log.i("MyLogService", "Token is valid")
            return true
        }
        else if(response.status.value == 401){
            Log.i("MyLogService", "Token is not valid")
            return false
        }
        throw getResponseException(response)
    }
}
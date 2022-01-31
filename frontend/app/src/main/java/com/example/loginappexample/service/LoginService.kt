package com.example.loginappexample.service

import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class LoginService : Service() {

    suspend fun sendLoginRequest(name:String, password:String): BasicUsersData{
        val route = "/api/users/"
        val response:HttpResponse = client.get(subdomain + route){
            headers {
                append(HttpHeaders.Authorization, encodeAuthHeader(name, password))
            }
        }
        if(response.status.value == 200) {
            val data:BasicUsersData = response.receive()
            Log.i("MyInfo", "Response body : $data")
            return data
        }
        throw getResponseException(response)
    }
}
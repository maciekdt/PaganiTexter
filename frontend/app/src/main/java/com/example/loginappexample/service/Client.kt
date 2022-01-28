package com.example.loginappexample.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class Client {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loginRequest(name:String, password:String){
        val auth = name+":"+password
        val encodedAuth: String = Base64.getEncoder().encodeToString(auth.toByteArray())
        val client = HttpClient()
        val response: HttpResponse = client.get("https://maciekdt.loca.lt/api/users/"){
            headers {
                append(HttpHeaders.Authorization, "Basic " + encodedAuth)
            }
        }

        Log.i("MyInfo", "Response status : " + response.status.toString())
        client.close()
    }
}
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

class LoginClient {

    private val subdomain = "https://maciekdt.loca.lt"

    private val client = HttpClient(CIO){
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        expectSuccess = false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loginRequest(name:String, password:String): BasicUsersData{
        val route = "/api/users/"
        val response:HttpResponse = client.get(subdomain + route){
            method = HttpMethod.Get
            headers {
                append(HttpHeaders.Authorization, encodeAuthHeader(name, password))
            }
        }
        return createResult(response)
    }

    private suspend fun createResult(response:HttpResponse):BasicUsersData{
        if(response.status.value == 200) {
            val data:BasicUsersData = response.receive()
            Log.i("MyInfo", "Response body : $data")
            return data
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeAuthHeader(name:String, password:String):String {
        val auth = "$name:$password"
        val encodedAuth:String = Base64.getEncoder().encodeToString(auth.toByteArray())
        return ("Basic $encodedAuth")
    }
}
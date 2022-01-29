package com.example.loginappexample.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class Client {

    private val subdomain = "https://maciekdt.loca.lt"

    private val client = HttpClient(CIO){
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        expectSuccess = false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loginRequest(name:String, password:String){
        val route = "/api/users/"
        val response:HttpResponse = client.get(subdomain + route){
            method = HttpMethod.Get
            headers {
                append(HttpHeaders.Authorization, encodeAuthHeader(name, password))
            }
        }
        val data:LoginResponseData = response.receive()
        Log.i("MyInfo", "Response status : " + response.status.toString())
        Log.i("MyInfo", "Response body : " + data.toString())
        //client.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeAuthHeader(name:String, password:String):String {
        val auth = "$name:$password"
        val encodedAuth:String = Base64.getEncoder().encodeToString(auth.toByteArray())
        return ("Basic $encodedAuth")
    }
}
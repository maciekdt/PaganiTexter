package com.example.loginappexample.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.loginappexample.service.exceptions.InternalServerException
import com.example.loginappexample.service.exceptions.NotAuthorizedException
import com.example.loginappexample.service.exceptions.NotFoundException
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.statement.*
import java.util.*

abstract class Service {

    companion object {
        @JvmStatic protected val subdomain = "https://maciekdt.loca.lt"

        @JvmStatic protected val client = HttpClient(CIO){
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            expectSuccess = false
        }
    }

    protected fun getResponseException(response:HttpResponse):Exception{
        if(response.status.value == 404) return NotFoundException("Status 404")
        if(response.status.value == 401) return NotAuthorizedException("Status 401")
        return InternalServerException("Status 500 or another")
    }

    protected fun encodeAuthHeader(name:String, password:String):String {
        val auth = "$name:$password"
        val encodedAuth:String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(auth.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return ("Basic $encodedAuth")
    }
}
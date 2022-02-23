package com.example.loginappexample.service

import com.example.loginappexample.data.model.RegisterData
import com.example.loginappexample.service.exceptions.EmailAlreadyUsedException
import com.example.loginappexample.service.exceptions.UsernameAlreadyUsedException
import com.example.loginappexample.service.model.ResponseError
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class RegisterService : Service(){

    suspend fun sendRegisterRequest(registrationData: RegisterData){
        val route = "/auth/register"
        var response: HttpResponse = client.post(subdomain + route){
            contentType(ContentType.Application.Json)
            body = registrationData
        }

        if(response.status.value == 400){
            val data: ResponseError = response.receive()
            if(data.errorCode == "USERNAME_ALREADY_USED")
                throw UsernameAlreadyUsedException("Status 400")
            else if(data.errorCode == "EMAIL_ALREADY_USED")
                throw  EmailAlreadyUsedException("Status 400")
        }
        else if(response.status.value != 201) throw getResponseException(response)
    }

}
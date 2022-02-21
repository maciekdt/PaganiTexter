package com.example.loginappexample.data

import com.example.loginappexample.data.model.RegisteredUser
import com.example.loginappexample.service.RegisterService


class RegisterDataSource{

    private val service = RegisterService()

    suspend fun register(registrationData: RegisteredUser){
        service.sendRegisterRequest(registrationData)
    }
}
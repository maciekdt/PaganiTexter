package com.example.loginappexample.data

import com.example.loginappexample.data.model.RegisterData
import com.example.loginappexample.service.RegisterService


class RegisterDataSource{

    private val service = RegisterService()

    suspend fun register(registrationData: RegisterData){
        service.sendRegisterRequest(registrationData)
    }
}
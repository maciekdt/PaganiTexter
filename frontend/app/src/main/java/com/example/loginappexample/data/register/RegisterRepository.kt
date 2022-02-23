package com.example.loginappexample.data.register

import com.example.loginappexample.data.model.RegisterData
import com.example.loginappexample.data.register.RegisterDataSource


class RegisterRepository(private val dataSource: RegisterDataSource) {

    suspend fun register(registrationData: RegisterData){
        dataSource.register(registrationData)
    }
}
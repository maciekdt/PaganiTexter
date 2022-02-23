package com.example.loginappexample.data

import com.example.loginappexample.data.model.RegisterData


class RegisterRepository(private val dataSource: RegisterDataSource) {

    suspend fun register(registrationData: RegisterData){
        dataSource.register(registrationData)
    }
}
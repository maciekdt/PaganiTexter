package com.example.loginappexample.data

import com.example.loginappexample.data.model.RegisteredUser


class RegisterRepository(private val dataSource: RegisterDataSource) {

    suspend fun register(registrationData: RegisteredUser){
        dataSource.register(registrationData)
    }
}
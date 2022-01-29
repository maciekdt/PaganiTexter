package com.example.loginappexample.service

data class LoginResponseData(val status:String, val message:String, val data:BasicUserData){
    override fun toString(): String {
        return data.toString()
    }
}




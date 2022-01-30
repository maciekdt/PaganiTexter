package com.example.loginappexample.service

data class ResponseState(val code:String, val message:String){
    override fun toString(): String {
        return "code : $code ($message)"
    }
}




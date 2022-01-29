package com.example.loginappexample.service

data class LoginResponseData(val status:String, val message:String, val data:LoginUserData){
    override fun toString(): String {
        return data.toString()
    }
}

data class LoginUserData(val id:Int, val name:String, val email:String, val password:String){
    override fun toString(): String {
        return "id : $id || name : $name || email : $email || password : $password"
    }
}


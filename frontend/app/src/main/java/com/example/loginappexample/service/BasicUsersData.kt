package com.example.loginappexample.service

data class BasicUsersData(val id:Int, val name:String, val email:String, val password:String){
    override fun toString(): String {
        return "id : $id || name : $name || email : $email || password : $password"
    }
}

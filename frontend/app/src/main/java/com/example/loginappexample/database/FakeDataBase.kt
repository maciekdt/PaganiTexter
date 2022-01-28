package com.example.loginappexample.database

import io.ktor.client.*
import io.ktor.client.engine.cio.*

class FakeDataBase : DataBase {
    private var users = ArrayList<DbUser>()

    init {
        users.add(DbUser("11111", "TestUser1","test11" , "testuser1@gmail.com"))
        users.add(DbUser("22222", "TestUser2","test22" , "testuser2@gmail.com"))
    }

    override fun login(email: String, password: String): DbResult<DbLoginErrorsRapport> {
        val httpClient: HttpClient = HttpClient(CIO)
        val user = findUserByEmail(email) ?: return DbResult.Error(DbLoginErrorsRapport("Email doesn't exist", LOGIN_WRONG_PASSWORD_OR_EMAIL))
        if(user.password != password) return DbResult.Error(DbLoginErrorsRapport("Wrong password", LOGIN_WRONG_PASSWORD_OR_EMAIL))
        return DbResult.Success(user)
    }

    override fun register(
        id: String,
        username: String,
        email: String,
        password: String
    ): DbResult<DbRegisterErrorsRapport> {
        var error = false
        val errorInfo = DbRegisterErrorsRapport(true,true,"","", null, null)
        if(findUserByEmail(email) != null){
            errorInfo.validEmail = false
            errorInfo.emailNote = "Email already exist"
            errorInfo.emailErrorId = REGISTER_EMAIL_ALREADY_EXIST
            error = true
        }
        if(findUserByName(username) != null){
            errorInfo.validUsername = false
            errorInfo.usernameNote = "Username already exist"
            errorInfo.usernameErrorId = REGISTER_USERNAME_ALREADY_EXIST
            error = true
        }
        return if(error) DbResult.Error(errorInfo)
        else{
            val user = DbUser(id,username,password, email)
            users.add(user)
            DbResult.Success(user)
        }
    }

    private fun findUserByEmail(email: String): DbUser? {
        for (user in users) {
            if (user.email == email) return user
        }
        return null
    }

    private fun findUserByName(name: String): DbUser? {
        for (user in users) {
            if (user.name == name) return user
        }
        return null
    }
}
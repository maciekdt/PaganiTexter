package com.example.loginappexample.ui.login

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.loginappexample.data.LoginRepository
import com.example.loginappexample.data.Result
import com.example.loginappexample.R
import com.example.loginappexample.service.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        sendRequest()
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            Log.i("MyInfo", "Logged in")
            _loginResult.value =
                LoginResult(success = LoggedInUserView(username = result.data.username))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed_wrong_pass)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendRequest() = runBlocking{
        val client: LoginClient = LoginClient()
        launch{
            client.loginRequest("WojtekTyper", "dupa")
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


}
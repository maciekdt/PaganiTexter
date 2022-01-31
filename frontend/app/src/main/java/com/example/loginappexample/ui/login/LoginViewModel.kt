package com.example.loginappexample.ui.login

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.loginappexample.data.LoginRepository
import com.example.loginappexample.data.Result
import com.example.loginappexample.R
import com.example.loginappexample.data.LoginDataSource
import com.example.loginappexample.service.LoginService
import com.example.loginappexample.service.exceptions.InternalServerException
import com.example.loginappexample.service.exceptions.NotAuthorizedException
import com.example.loginappexample.service.exceptions.NotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repo: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String){
        viewModelScope.launch{
            try {
                val data = repo.login(username, password)
                Log.i("MyLoginInfo", "Logged in")
                _loginResult.value = LoginResult(success = R.string.login_success)
            }
            catch (e : NotAuthorizedException){
                Log.i("MyLoginInfo", "Not Authorized")
                _loginResult.value = LoginResult(error = R.string.not_authorized_error)
            }
            catch (e : NotFoundException){
                Log.i("MyLoginInfo", "Not Found")
                _loginResult.value = LoginResult(error = R.string.not_found_error)
            }
            catch (e : InternalServerException){
                Log.i("MyLoginInfo", "Internal Server Error")
                _loginResult.value = LoginResult(error = R.string.internal_server_error)
            }

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
        return password.length > 3
    }


}
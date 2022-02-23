package com.example.loginappexample.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.loginappexample.data.login.LoginRepository
import com.example.loginappexample.R
import com.example.loginappexample.service.exceptions.InternalServerException
import com.example.loginappexample.service.exceptions.NotAuthorizedException
import com.example.loginappexample.service.exceptions.NotFoundException
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String, rememberMe: Boolean){
        viewModelScope.launch{
            try {
                if(repo.loginByCache()){
                    Log.i("MyLogModel", "Logged in by cache")
                    _loginResult.value = LoginResult(error =  R.string.login_success_cache)
                }
                else{
                    Log.w("MyLogModel", "Invalid token in cache")
                    val data = repo.login(username, password, rememberMe)
                    Log.i("MyLogModel", "Logged in")
                    _loginResult.value = LoginResult(success = R.string.login_success)
                }
            }
            catch (e : NotAuthorizedException){
                Log.e("MyLogModel", "Not Authorized")
                _loginResult.value = LoginResult(error = R.string.not_authorized_error)
            }
            catch (e : NotFoundException){
                Log.e("MyLogModel", "Not Found")
                _loginResult.value = LoginResult(error = R.string.not_found_error)
            }
            catch (e : InternalServerException){
                Log.e("MyLogModel", "Internal Server Error")
                _loginResult.value = LoginResult(error = R.string.internal_server_error)
            }

        }
    }

    fun loginByCache(){
        viewModelScope.launch{
            try {
                if(repo.loginByCache()){
                    Log.i("MyLogModel", "Logged in by cache")
                    _loginResult.value = LoginResult(error =  R.string.login_success_cache)
                }
                else{
                    _loginResult.value = LoginResult(error = R.string.login_error_cache)
                }
            }
            catch (e : NotAuthorizedException){
                Log.e("MyLogModel", "Not Authorized")
                _loginResult.value = LoginResult(error = R.string.not_authorized_error)
            }
            catch (e : NotFoundException){
                Log.e("MyLogModel", "Not Found")
                _loginResult.value = LoginResult(error = R.string.not_found_error)
            }
            catch (e : InternalServerException){
                Log.e("MyLogModel", "Internal Server Error")
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



    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }


}
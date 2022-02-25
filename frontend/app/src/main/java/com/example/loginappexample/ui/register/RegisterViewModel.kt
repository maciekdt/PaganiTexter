package com.example.loginappexample.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginappexample.R
import com.example.loginappexample.data.register.RegisterRepository
import com.example.loginappexample.data.model.RegisterData
import com.example.loginappexample.service.exceptions.*
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: RegisterRepository) : ViewModel(){

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerForm:LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult:LiveData<RegisterResult> = _registerResult

    fun register(data: RegisterFormStateData){
        val requestData = RegisterData(
            data.username,
            data.password,
            data.email
        )
        viewModelScope.launch{
            try{
                repo.register(requestData)
                _registerResult.value = RegisterResult(success = R.string.register_success)
                Log.i("MyLogModel", "User registered")
            }
            catch (e : InternalServerException){
                _registerResult.value = RegisterResult(error = R.string.register_server_error)
                Log.e("MyLogModel", "Internal Server Error")
            }
            catch (e : EmailAlreadyUsedException){
                _registerResult.value = RegisterResult(
                    error = R.string.register_invalid_data,
                    emailError = R.string.register_email_already_used)
                Log.e("MyLogModel", "Email already used")
            }
            catch (e : UsernameAlreadyUsedException){
                _registerResult.value = RegisterResult(
                    error = R.string.register_invalid_data,
                    usernameError = R.string.register_username_already_used)
                Log.e("MyLogModel", "Username already used")
            }

        }
    }

    fun registerDataChange(data: RegisterFormStateData){
        if(!isUsernameCorrect(data.username))
            _registerForm.value = RegisterFormState(usernameError = R.string.register_invalid_username)
        else if(!isEmailCorrect(data.email))
            _registerForm.value = RegisterFormState(emailError = R.string.register_invalid_email)
        else if(!isPasswordCorrect(data.password))
            _registerForm.value = RegisterFormState(passwordError = R.string.register_invalid_password)
        else if(!isRepeatPasswordCorrect(data.password, data.repeatPassword))
            _registerForm.value = RegisterFormState(repeatPasswordError = R.string.register_invalid_repeat_password)
        else
            _registerForm.value = RegisterFormState(isDataValid = true)
    }

    private fun isUsernameCorrect(name: String): Boolean{
        return name.isNotBlank()
    }

    private fun isEmailCorrect(email: String): Boolean{
        return if(email.isBlank()) false
        else Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordCorrect(password: String): Boolean{
        return password.length >= 4
    }

    private fun isRepeatPasswordCorrect(password: String, repPassword: String): Boolean{
        return password == repPassword;
    }
}
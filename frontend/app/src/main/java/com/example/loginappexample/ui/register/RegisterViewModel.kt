package com.example.loginappexample.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginappexample.R
import com.example.loginappexample.data.RegisterRepository
import com.example.loginappexample.data.model.RegisteredUser
import com.example.loginappexample.service.exceptions.*
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo:RegisterRepository) : ViewModel(){

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerForm:LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult:LiveData<RegisterResult> = _registerResult

    fun register(data: RegisterFormStateData){
        val requestData = RegisteredUser(
            data.username,
            data.password,
            data.email
        )
        viewModelScope.launch{
            try{
                repo.register(requestData)
                Log.i("MyLogModel", "User registered")
            }
            catch (e : NotAuthorizedException){
                Log.e("MyLogModel", "Not Authorized")
            }
            catch (e : NotFoundException){
                Log.e("MyLogModel", "Not Found")
            }
            catch (e : InternalServerException){
                Log.e("MyLogModel", "Internal Server Error")
            }
            catch (e : EmailAlreadyUsedException){
                Log.e("MyLogModel", "Email already used")
                _registerResult.value = RegisterResult(error = R.string.register_email_already_used)
            }
            catch (e : UsernameAlreadyUsedException){
                Log.e("MyLogModel", "Username already used")
                _registerResult.value = RegisterResult(error = R.string.register_username_already_used)
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
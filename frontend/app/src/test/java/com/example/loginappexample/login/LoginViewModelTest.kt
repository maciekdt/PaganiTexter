package com.example.loginappexample.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.loginappexample.R
import com.example.loginappexample.data.login.LoginLocalDataSource
import com.example.loginappexample.data.login.LoginRemoteDataSource
import com.example.loginappexample.data.login.LoginRepository
import com.example.loginappexample.ui.login.LoginFormState
import com.example.loginappexample.ui.login.LoginViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {
    private lateinit var model: LoginViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        model = LoginViewModel(LoginRepository(LoginRemoteDataSource(), LoginLocalDataSource()))
    }


    @Test
    fun loginDataChanged_isCorrect(){

        model.loginDataChanged("John", "pass")
        Assert.assertEquals(LoginFormState(isDataValid = true), model.loginFormState.value)

        model.loginDataChanged("hHy67f7G", "UH88ygYhJk7")
        Assert.assertEquals(LoginFormState(isDataValid = true), model.loginFormState.value)

        model.loginDataChanged("", "pass")
        Assert.assertEquals(LoginFormState(usernameError = R.string.invalid_username), model.loginFormState.value)

        model.loginDataChanged("Mark", "pas")
        Assert.assertEquals(LoginFormState(passwordError = R.string.invalid_password), model.loginFormState.value)

        model.loginDataChanged("Mark", "")
        Assert.assertEquals(LoginFormState(passwordError = R.string.invalid_password), model.loginFormState.value)

        model.loginDataChanged("", "pa")
        Assert.assertEquals(LoginFormState(usernameError = R.string.invalid_username), model.loginFormState.value)


    }
}
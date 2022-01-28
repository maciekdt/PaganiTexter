package com.example.loginappexample.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.loginappexample.databinding.ActivityLoginBinding

import com.example.loginappexample.R
import com.example.loginappexample.service.Client
import com.example.loginappexample.ui.register.RegisterActivity
import kotlinx.coroutines.Dispatchers

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val registerButton = binding.register
        val forgetPassButton = binding.button3
        val loginInfoText = binding.loginResultInfo
        val loadingProgressBar = binding.loading



        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                usernameEditText.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordEditText.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loadingProgressBar.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
                loginInfoText.error = getString(loginResult.error)
                loginInfoText.text = getString(loginResult.error)
                loginButton.isEnabled = false
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        usernameEditText.afterTextChanged {
            loginViewModel.loginDataChanged(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        passwordEditText.afterTextChanged {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
        }


        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgetPassButton.setOnClickListener {
            //LoginViewModel.
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val username = model.username
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $username",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


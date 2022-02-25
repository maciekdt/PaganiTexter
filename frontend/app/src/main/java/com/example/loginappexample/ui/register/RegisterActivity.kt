package com.example.loginappexample.ui.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginappexample.R
import com.example.loginappexample.databinding.ActivityRegisterBinding
import com.example.loginappexample.ui.login.LoginViewModel
import com.example.loginappexample.ui.login.LoginViewModelFactory
import java.io.File

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText = binding.username
        val emailEditText = binding.email
        val passwordEditText = binding.password
        val repeatPasswordEditText = binding.repeatPassword
        val registerButton = binding.register
        val agreeCheckBox = binding.agree
        val loadingProgressBar = binding.registerLoading
        val registerInfoText = binding.registerResultInfo

        registerViewModel =
            ViewModelProvider(this,RegisterViewModelFactory())[RegisterViewModel::class.java]

        loadingProgressBar.visibility = View.GONE

        registerViewModel.registerForm.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            registerButton.isEnabled = registerState.isDataValid

            if(registerState.usernameError != null){
                usernameEditText.error = getString(registerState.usernameError)
            }
            if(registerState.emailError != null){
                emailEditText.error = getString(registerState.emailError)
            }
            if(registerState.passwordError != null){
                passwordEditText.error = getString(registerState.passwordError)
            }
            if(registerState.repeatPasswordError != null){
                repeatPasswordEditText.error = getString(registerState.repeatPasswordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer
            loadingProgressBar.visibility = View.GONE

            if(registerResult.error != null) {
                registerInfoText.error = getString(registerResult.error)
                registerInfoText.text = getString(registerResult.error)

                if (registerResult.usernameError != null) {
                    usernameEditText.error = getString(registerResult.usernameError)
                } else if (registerResult.emailError != null) {
                    emailEditText.error = getString(registerResult.emailError)
                }
            }
            else if(registerResult.success != null){
                Toast.makeText(applicationContext, getString(registerResult.success), Toast.LENGTH_LONG).show()
            }
        })

        fun getRegisterData(): RegisterFormStateData{
            return RegisterFormStateData(
                usernameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                repeatPasswordEditText.text.toString(),
                agreeCheckBox.isChecked
            )
        }

        usernameEditText.afterTextChanged{
            registerViewModel.registerDataChange(getRegisterData())
        }

        emailEditText.afterTextChanged{
            registerViewModel.registerDataChange(getRegisterData())
        }

        passwordEditText.afterTextChanged{
            registerViewModel.registerDataChange(getRegisterData())
        }

        repeatPasswordEditText.afterTextChanged{
            registerViewModel.registerDataChange(getRegisterData())
        }

        registerButton.setOnClickListener{
            loadingProgressBar.visibility = View.VISIBLE
            registerViewModel.register(getRegisterData())
        }


    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }


}
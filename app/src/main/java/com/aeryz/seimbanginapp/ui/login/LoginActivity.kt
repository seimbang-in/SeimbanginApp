package com.aeryz.seimbanginapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityLoginBinding
import com.aeryz.seimbanginapp.ui.main.MainActivity
import com.aeryz.seimbanginapp.ui.register.RegisterActivity
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.highLightWord
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
        observeLoginResult()
        observeUserToken()
    }

    private fun setOnClickListener() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.textNavigateToRegister.highLightWord(getString(R.string.text_register)) {
            navigateToRegister()
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.btnLogin.isVisible = true
                    binding.pbLoading.isVisible = false
                    it.payload?.loginData?.token?.let { token ->
                        viewModel.saveToken(token)
                    }
                    it.payload?.loginData?.expiresIn?.let { expiresIn ->
                        val currentTime = System.currentTimeMillis()
                        val expiresTime = currentTime + (expiresIn * 1000)
                        viewModel.saveTokenExpiresTime(expiresTime)
                    }
                    navigateToHome()
                },
                doOnError = {
                    binding.btnLogin.isVisible = true
                    binding.pbLoading.isVisible = false
                    if (it.exception is ApiException) {
                        FancyToast.makeText(
                            this,
                            it.exception.getParsedError()?.message,
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                },
                doOnLoading = {
                    binding.btnLogin.isVisible = false
                    binding.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun observeUserToken() {
        viewModel.userToken.observe(this) { token ->
            if (token != null) navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doLogin() {
        if (isFormValid()) {
            val identifier = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(identifier, password)
        }
    }

    private fun isFormValid(): Boolean {
        val identifier = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        return checkIdentifierValidation(identifier) && checkPasswordValidation(password)
    }

    private fun checkIdentifierValidation(identifier: String): Boolean {
        return if (identifier.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_email_username_still_empty)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            binding.tilPassword.isErrorEnabled = false
            true
        }
    }
}

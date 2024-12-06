package com.aeryz.seimbanginapp.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.aeryz.seimbanginapp.databinding.ActivitySplashBinding
import com.aeryz.seimbanginapp.ui.login.LoginActivity
import com.aeryz.seimbanginapp.ui.main.MainActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIfUserLogin()
        observeDarkModePref()
        setBottomText()
    }

    private fun setBottomText() {
        val spannableString = SpannableString("bangkit.academy")
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            7,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            7,
            spannableString.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvBangkit.text = spannableString
    }

    private fun checkIfUserLogin() {
        viewModel.isUserLoggedIn.observe(this) { token ->
            if (token.isNullOrEmpty()) {
                navigateToLogin()
            } else {
                viewModel.tokenExpiresTime.observe(this) { expiresTime ->
                    val currentTime = System.currentTimeMillis()
                    if (currentTime >= expiresTime) {
                        viewModel.deleteToken()
                        viewModel.deleteTokenExpires()
                    } else {
                        navigateToMain()
                    }
                }
            }
        }
    }

    private fun observeDarkModePref() {
        lifecycleScope.launch {
            val darkMode = viewModel.userDarkMode.firstOrNull() ?: false
            AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
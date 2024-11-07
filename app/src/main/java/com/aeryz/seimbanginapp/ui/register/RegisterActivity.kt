package com.aeryz.seimbanginapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityRegisterBinding
import com.aeryz.seimbanginapp.ui.login.LoginActivity
import com.aeryz.seimbanginapp.utils.highLightWord

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.textNavigateToLogin.highLightWord(getString(R.string.text_login)) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

}
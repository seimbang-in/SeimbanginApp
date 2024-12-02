package com.aeryz.seimbanginapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityMainBinding
import com.aeryz.seimbanginapp.ui.chatAi.ChatAiActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        observeDarkMode()
        setupBottomNav()
    }

    private fun setupAction() {
        binding.fabAskAi.setOnClickListener {
            val intent = Intent(this, ChatAiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    private fun observeDarkMode() {
        viewModel.userDarkModeLiveData.observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}
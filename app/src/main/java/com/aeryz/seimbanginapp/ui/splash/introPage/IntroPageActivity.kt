package com.aeryz.seimbanginapp.ui.splash.introPage

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityIntroPageBinding
import com.aeryz.seimbanginapp.ui.login.LoginActivity
import com.aeryz.seimbanginapp.ui.register.RegisterActivity
import com.aeryz.seimbanginapp.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroPageBinding

    private val viewModel: SplashViewModel by viewModel()

    private val introPageAdapter: IntroPageAdapter by lazy {
        IntroPageAdapter(viewModel.getIntroPageData())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewpagerIntroSlider.adapter = introPageAdapter
        setupIndicators()
        setCurrentIndicator(0)
        changePageViewPager()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
            viewModel.setShouldShowIntroPage(false)
        }
        binding.btnRegister.setOnClickListener {
            navigateToRegister()
            viewModel.setShouldShowIntroPage(false)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introPageAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.slider_indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorSlider.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorSlider.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorSlider[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.slider_indicator_inactive
                    )
                )
            }
        }
    }

    private fun changePageViewPager() {
        binding.viewpagerIntroSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            }
        )
    }
}
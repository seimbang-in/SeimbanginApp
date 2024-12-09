package com.aeryz.seimbanginapp.data.local.datasource

import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.model.IntroSliderItem

interface IntroPageDataSource {
    fun getIntroPageData(): List<IntroSliderItem>
}

class IntroPageDataSourceImpl() : IntroPageDataSource {
    override fun getIntroPageData(): List<IntroSliderItem> {
        return listOf(
            IntroSliderItem(
                R.drawable.intropage_image_1,
                "Track Your Finances, Effortlessly!",
                "Simple and intuitive design makes managing your money stress-free. Perfect for everyone!"
            ),
            IntroSliderItem(
                R.drawable.intropage_image_2,
                "Automate Your Tracking!",
                "Simply scan your purchase receipts, and we’ll handle the rest. Fast, accurate, and hassle-free!"
            ),
            IntroSliderItem(
                R.drawable.intropage_image_3,
                "Know Where Your Money Goes!",
                "Easily see your income, expenses, and financial trends in one place. Stay in control of your budget!"
            ),
        )
    }
}
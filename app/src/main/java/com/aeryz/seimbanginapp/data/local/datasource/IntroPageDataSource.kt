package com.aeryz.seimbanginapp.data.local.datasource

import android.content.Context
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.model.IntroSliderItem

interface IntroPageDataSource {
    fun getIntroPageData(context: Context): List<IntroSliderItem>
}

class IntroPageDataSourceImpl : IntroPageDataSource {
    override fun getIntroPageData(context: Context): List<IntroSliderItem> =
        listOf(
            IntroSliderItem(
                R.drawable.image_onboard1,
                context.getString(R.string.text_title_onboarding_1),
                context.getString(R.string.text_desc_onboarding_1),
            ),
            IntroSliderItem(
                R.drawable.image_onboard2,
                context.getString(R.string.text_title_onboarding_2),
                context.getString(R.string.text_desc_onboarding_2),
            ),
            IntroSliderItem(
                R.drawable.image_onboard3,
                context.getString(R.string.text_title_onboarding_3),
                context.getString(R.string.text_desc_onboarding_3),
            ),
        )
}

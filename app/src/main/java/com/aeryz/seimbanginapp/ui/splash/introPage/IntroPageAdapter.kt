package com.aeryz.seimbanginapp.ui.splash.introPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.seimbanginapp.databinding.ItemIntroSliderBinding
import com.aeryz.seimbanginapp.model.IntroSliderItem

class IntroPageAdapter(
    private val introSlide: List<IntroSliderItem>
) : RecyclerView.Adapter<IntroPageAdapter.IntroPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroPageViewHolder {
        val binding =
            ItemIntroSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroPageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }

    override fun onBindViewHolder(holder: IntroPageViewHolder, position: Int) {
        holder.bind(introSlide[position])
    }

    inner class IntroPageViewHolder(
        private val binding: ItemIntroSliderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(introSliderItem: IntroSliderItem) {
            binding.ivSliderImage.setImageResource(introSliderItem.icon)
            binding.tvSliderTitle.text = introSliderItem.title
            binding.tvSliderDescription.text = introSliderItem.description
        }
    }
}
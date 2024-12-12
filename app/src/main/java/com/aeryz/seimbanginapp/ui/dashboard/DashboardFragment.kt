package com.aeryz.seimbanginapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabIndicator()
    }

    private fun setTabIndicator() {
        val viewPager = binding.viewPager
        val adapter = DashboardViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPage.text = when (position) {
                    0 -> getString(R.string.text_income_capital)
                    1 -> getString(R.string.text_outcome_capital)
                    else -> ""
                }
            }
        })
        binding.btnPrevious.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }
        binding.btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < DashboardViewPagerAdapter.totalTabs - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

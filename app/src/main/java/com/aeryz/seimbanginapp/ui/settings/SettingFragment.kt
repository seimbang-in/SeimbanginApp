package com.aeryz.seimbanginapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.aeryz.seimbanginapp.databinding.FragmentSettingBinding
import com.aeryz.seimbanginapp.ui.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SettingFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        observeDarkMode()
    }

    private fun observeDarkMode() {
        mainViewModel.userDarkModeLiveData.observe(this) { isUsingDarkMode ->
            if (isUsingDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swDarkMode.isChecked = false
            }
        }
    }

    private fun setupAction() {
        binding.swDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isUsingDarkMode: Boolean ->
            mainViewModel.setUserDarkModePref(isUsingDarkMode)
        }
        binding.tvLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
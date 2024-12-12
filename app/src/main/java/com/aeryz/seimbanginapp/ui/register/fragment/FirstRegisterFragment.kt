package com.aeryz.seimbanginapp.ui.register.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.FragmentFirstRegisterBinding

class FirstRegisterFragment : Fragment() {
    private var _binding: FragmentFirstRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            navigateToSecondRegisterFragment()
        }
    }

    private fun navigateToSecondRegisterFragment() {
        val fullName = binding.etFullName.text.toString().trim()
        val userName = binding.etUsername.text.toString().trim()
        if (checkFullNameValidation(fullName) && checkUserNameValidation(userName)) {
            val action =
                FirstRegisterFragmentDirections.actionFirstRegisterFragmentToSecondRegisterFragment(
                    fullName,
                    userName
                )
            findNavController().navigate(action)
        }
    }

    private fun checkFullNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.tilFullName.isErrorEnabled = true
            binding.tilFullName.error = getString(R.string.text_hint_full_name_empty)
            false
        } else {
            binding.tilFullName.isErrorEnabled = false
            true
        }
    }

    private fun checkUserNameValidation(userName: String): Boolean {
        return if (userName.isEmpty()) {
            binding.tilUsername.isErrorEnabled = true
            binding.tilUsername.error = getString(R.string.text_hint_user_name_empty)
            false
        } else {
            binding.tilUsername.isErrorEnabled = false
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

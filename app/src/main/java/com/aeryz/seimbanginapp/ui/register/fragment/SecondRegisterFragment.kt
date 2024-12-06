package com.aeryz.seimbanginapp.ui.register.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.FragmentSecondRegisterBinding
import com.aeryz.seimbanginapp.ui.login.LoginActivity
import com.aeryz.seimbanginapp.ui.register.RegisterViewModel
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SecondRegisterFragment : Fragment() {
    private var _binding: FragmentSecondRegisterBinding? = null
    private val binding get() = _binding!!

    private val args: SecondRegisterFragmentArgs by navArgs()

    private val viewModel: RegisterViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeRegisterResult()
    }

    private fun observeRegisterResult() {
        viewModel.registerResult.observe(viewLifecycleOwner) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.btnRegister.isVisible = true
                    binding.pbLoading.isVisible = false
                    customPopup(1, null)
                },
                doOnLoading = {
                    binding.btnRegister.isVisible = false
                    binding.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.btnRegister.isVisible = true
                    binding.pbLoading.isVisible = false
                    if (it.exception is ApiException) {
                        customPopup(0, it.exception.getParsedError()?.message.orEmpty())
                    }
                }
            )
        }
    }

    private fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val fullName = args.fullName
            val userName = args.userName
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.register(fullName, userName, email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        return checkEmailValidation(email) && checkPasswordValidation(password)
                && checkConfirmPasswordValidation(password, confirmPassword)
    }

    private fun checkConfirmPasswordValidation(password: String, confirmPassword: String): Boolean {
        return if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error =
                getString(R.string.text_error_password_less_than_8_char)
            false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error = getString(R.string.text_error_password_not_match)
            false
        } else {
            binding.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            binding.tilPassword.isErrorEnabled = false
            true
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun customPopup(type: Int, message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_layout_popup)
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams
        val icon = dialog.findViewById<ImageView>(R.id.iv_icon)
        val title = dialog.findViewById<TextView>(R.id.tv_title)
        val description = dialog.findViewById<TextView>(R.id.tv_description)
        val button = dialog.findViewById<Button>(R.id.btn_continue)
        if (type == 1) {
            icon.load(R.drawable.ic_success)
            title.text = getString(R.string.text_success)
            description.text = getString(R.string.text_successfully_creating_the_account)
            button.setOnClickListener {
                dialog.dismiss()
                navigateToLogin()
            }
        } else {
            icon.load(R.drawable.ic_failed)
            title.text = getString(R.string.text_failed)
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.error_500))
            description.text = getString(R.string.text_failed_creating_account, message)
            button.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.error_500)
            button.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
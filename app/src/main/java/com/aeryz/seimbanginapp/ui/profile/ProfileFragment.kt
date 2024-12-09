package com.aeryz.seimbanginapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.FragmentProfileBinding
import com.aeryz.seimbanginapp.ui.login.LoginActivity
import com.aeryz.seimbanginapp.ui.settings.SettingFragment
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfileData()
        observeProfileData()
        setOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        getProfileData()
    }

    private fun setOnClickListener() {
        binding.tvLogout.setOnClickListener {
            viewModel.doLogout()
            viewModel.deleteAllTransactionFromDb()
            viewModel.deleteAdviseFromDb()
            navigateToLogin()
        }
        binding.tvEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
        binding.tvSetting.setOnClickListener {
            SettingFragment().show(childFragmentManager, null)
        }
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.profileContent.isVisible = true
                    binding.contentState.root.isVisible = false
                    it.payload.let { response ->
                        binding.profileImage.load(response?.profileData?.profilePicture) {
                            crossfade(true)
                            placeholder(R.drawable.profile_image)
                            error(R.drawable.profile_image)
                            transformations(CircleCropTransformation())
                        }
                        binding.tvProfileName.text = response?.profileData?.fullName
                        binding.tvProfileEmail.text = response?.profileData?.email
                        binding.tvFinancialProfile.setOnClickListener {
                            navigateToFinancialProfile()
                        }
                    }
                },
                doOnLoading = {
                    binding.profileContent.isVisible = false
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.contentState.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.profileContent.isVisible = false
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                }
            )
        }
    }

    private fun getProfileData() {
        viewModel.getProfileData()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToFinancialProfile() {
        val action = ProfileFragmentDirections.actionNavigationProfileToFinancialProfileActivity()
        findNavController().navigate(action)
    }

    private fun navigateToEditProfile() {
        val action = ProfileFragmentDirections.actionNavigationProfileToEditProfileActivity()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
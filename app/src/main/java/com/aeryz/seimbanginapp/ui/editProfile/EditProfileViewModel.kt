package com.aeryz.seimbanginapp.ui.editProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.editProfile.EditProfileResponse
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.network.model.uploadProfileImage.UploadProfileImageResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _editProfileResult = MutableLiveData<ResultWrapper<EditProfileResponse>>()
    val editProfileResult: LiveData<ResultWrapper<EditProfileResponse>> = _editProfileResult

    private val _profileData = MutableLiveData<ResultWrapper<ProfileResponse>>()
    val profileData: LiveData<ResultWrapper<ProfileResponse>> = _profileData

    private val _uploadProfileImageResult =
        MutableLiveData<ResultWrapper<UploadProfileImageResponse>>()
    val uploadProfileImageResult: LiveData<ResultWrapper<UploadProfileImageResponse>> =
        _uploadProfileImageResult

    var currentImageUri: Uri? = null

    fun editProfile(fullName: String) {
        viewModelScope.launch {
            repository.editProfile(fullName).collect {
                _editProfileResult.postValue(it)
            }
        }
    }

    fun getProfileData() {
        viewModelScope.launch {
            repository.getUserProfile().collect {
                _profileData.postValue(it)
            }
        }
    }

    fun uploadProfileImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            repository.uploadProfileImage(image).collect {
                _uploadProfileImageResult.postValue(it)
            }
        }
    }
}
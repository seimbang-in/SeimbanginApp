package com.aeryz.seimbanginapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.data.repository.LocalTransactionRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AuthRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val localTransactionRepository: LocalTransactionRepository
) : ViewModel() {

    private val _profileData = MutableLiveData<ResultWrapper<ProfileResponse>>()
    val profileData: LiveData<ResultWrapper<ProfileResponse>>
        get() = _profileData

    fun getProfileData() {
        viewModelScope.launch {
            repository.getUserProfile().collect {
                _profileData.postValue(it)
            }
        }
    }

    fun doLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.deleteToken()
        }
    }

    fun deleteAllTransactionFromDb() {
        viewModelScope.launch {
            localTransactionRepository.deleteAllTransaction()
        }
    }
}
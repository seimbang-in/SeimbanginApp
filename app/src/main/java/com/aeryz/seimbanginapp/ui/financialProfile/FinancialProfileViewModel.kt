package com.aeryz.seimbanginapp.ui.financialProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.financialProfile.FinancialProfileResponse
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class FinancialProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _updateResult = MutableLiveData<ResultWrapper<FinancialProfileResponse>>()
    val updateResult: LiveData<ResultWrapper<FinancialProfileResponse>>
        get() = _updateResult

    private val _profileData = MutableLiveData<ResultWrapper<ProfileResponse>>()
    val profileData: LiveData<ResultWrapper<ProfileResponse>>
        get() = _profileData

    fun updateFinancialProfile(
        monthlyIncome: Double,
        currentSavings: Double,
        debt: Double,
        financialGoals: String,
        riskManagementType: String
    ) {
        viewModelScope.launch {
            repository.updateFinancialProfile(
                monthlyIncome,
                currentSavings,
                debt,
                financialGoals,
                riskManagementType
            ).collect {
                _updateResult.postValue(it)
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
}

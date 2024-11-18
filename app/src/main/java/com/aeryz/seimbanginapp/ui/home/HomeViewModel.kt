package com.aeryz.seimbanginapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.TransactionHistoryResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.data.repository.TransactionRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _profileData = MutableLiveData<ResultWrapper<ProfileResponse>>()
    val profileData: LiveData<ResultWrapper<ProfileResponse>>
        get() = _profileData

    var currentLimit: Int = 5
    var currentPage: Int = 1
    private val _transactionHistory = MutableLiveData<ResultWrapper<TransactionHistoryResponse>>()
    val transactionHistory: LiveData<ResultWrapper<TransactionHistoryResponse>> =
        _transactionHistory

    fun getProfileData() {
        viewModelScope.launch {
            authRepository.getUserProfile().collect {
                _profileData.postValue(it)
            }
        }
    }

    fun getTransactionHistory(limit: Int?, page: Int?) {
        viewModelScope.launch {
            transactionRepository.getTransactionHistory(limit, page).collect {
                _transactionHistory.postValue(it)
            }
        }
    }
}
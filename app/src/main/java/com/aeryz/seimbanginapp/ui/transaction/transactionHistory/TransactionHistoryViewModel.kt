package com.aeryz.seimbanginapp.ui.transaction.transactionHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.TransactionHistoryResponse
import com.aeryz.seimbanginapp.data.repository.TransactionRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class TransactionHistoryViewModel(private val repository: TransactionRepository) : ViewModel() {
    var currentLimit: Int = 10
    var currentPage: Int = 1
    private val _transactionHistory = MutableLiveData<ResultWrapper<TransactionHistoryResponse>>()
    val transactionHistory: LiveData<ResultWrapper<TransactionHistoryResponse>> =
        _transactionHistory

    fun getTransactionHistory(limit: Int?, page: Int?) {
        viewModelScope.launch {
            repository.getTransactionHistory(limit, page).collect {
                _transactionHistory.postValue(it)
            }
        }
    }
}
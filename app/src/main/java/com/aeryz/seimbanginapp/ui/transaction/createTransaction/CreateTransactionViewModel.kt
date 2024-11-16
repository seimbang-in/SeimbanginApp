package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionResponse
import com.aeryz.seimbanginapp.data.repository.TransactionRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class CreateTransactionViewModel(private val repository: TransactionRepository) : ViewModel() {
    private val _createTransactionResult =
        MutableLiveData<ResultWrapper<CreateTransactionResponse>>()
    val createTransactionResult: LiveData<ResultWrapper<CreateTransactionResponse>> =
        _createTransactionResult

    fun createTransaction(
        type: Int,
        category: String,
        amount: Double,
        description: String
    ) {
        viewModelScope.launch {
            repository.createTransaction(type, category, amount, description).collect{
                _createTransactionResult.postValue(it)
            }
        }
    }
}
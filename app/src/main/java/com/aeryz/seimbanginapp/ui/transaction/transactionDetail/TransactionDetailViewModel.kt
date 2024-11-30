package com.aeryz.seimbanginapp.ui.transaction.transactionDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.database.entity.TransactionEntity
import com.aeryz.seimbanginapp.data.network.model.deleteTransaction.DeleteTransactionResponse
import com.aeryz.seimbanginapp.data.repository.LocalTransactionRepository
import com.aeryz.seimbanginapp.data.repository.TransactionRepository
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    private val repository: TransactionRepository,
    private val localTransactionRepository: LocalTransactionRepository
) : ViewModel() {
    private val _deleteTransactionResult =
        MutableLiveData<ResultWrapper<DeleteTransactionResponse>>()
    val deleteTransactionResult: LiveData<ResultWrapper<DeleteTransactionResponse>> =
        _deleteTransactionResult

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            repository.deleteTransaction(id).collect {
                _deleteTransactionResult.postValue(it)
            }
        }
    }

    fun deleteTransactionFromDb(id: Int) {
        viewModelScope.launch {
            localTransactionRepository.deleteTransaction(id)
        }
    }
}
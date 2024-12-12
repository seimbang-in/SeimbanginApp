package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionRequest
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionResponse
import com.aeryz.seimbanginapp.data.network.model.createTransaction.TransactionItemRequest
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
        name: String,
        items: List<TransactionItemRequest>
    ) {
        viewModelScope.launch {
            val request = CreateTransactionRequest(type, name, items)
            repository.createTransaction(request).collect {
                _createTransactionResult.postValue(it)
            }
        }
    }
}

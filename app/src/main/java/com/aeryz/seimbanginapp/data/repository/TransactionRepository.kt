package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSource
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionRequest
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionResponse
import com.aeryz.seimbanginapp.data.network.model.deleteTransaction.DeleteTransactionResponse
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.TransactionHistoryResponse
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun createTransaction(createTransactionRequest: CreateTransactionRequest): Flow<ResultWrapper<CreateTransactionResponse>>
    fun getTransactionHistory(limit: Int?, page: Int?): Flow<ResultWrapper<TransactionHistoryResponse>>
    fun deleteTransaction(id: Int): Flow<ResultWrapper<DeleteTransactionResponse>>
}

class TransactionRepositoryImpl(private val dataSource: SeimbanginDataSource): TransactionRepository {
    override fun createTransaction(createTransactionRequest: CreateTransactionRequest): Flow<ResultWrapper<CreateTransactionResponse>> {
        return proceedFlow {
            dataSource.createTransaction(createTransactionRequest)
        }
    }

    override fun getTransactionHistory(
        limit: Int?,
        page: Int?
    ): Flow<ResultWrapper<TransactionHistoryResponse>> {
        return proceedFlow {
            dataSource.getTransactionHistory(limit, page)
        }
    }

    override fun deleteTransaction(id: Int): Flow<ResultWrapper<DeleteTransactionResponse>> {
        return proceedFlow {
            dataSource.deleteTransaction(id)
        }
    }

}
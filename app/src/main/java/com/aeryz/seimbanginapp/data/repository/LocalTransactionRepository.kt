package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.data.local.database.dao.TransactionDao
import com.aeryz.seimbanginapp.data.local.database.entity.TransactionEntity
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceed
import com.aeryz.seimbanginapp.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface LocalTransactionRepository {
    fun getTransactionByType(type: Int): Flow<ResultWrapper<List<TransactionEntity>>>
    fun insertListTransaction(transactions: List<TransactionEntity>): Flow<ResultWrapper<Boolean>>
    fun insertTransaction(transactionEntity: TransactionEntity): Flow<ResultWrapper<Boolean>>
    suspend fun deleteTransaction(id: Int)
    suspend fun deleteAllTransaction()
}

class LocalTransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : LocalTransactionRepository {
    override fun getTransactionByType(type: Int): Flow<ResultWrapper<List<TransactionEntity>>> {
        return transactionDao.getTransactionByType(type)
            .map { transactionList ->
                proceed {
                    transactionList
                }
            }.map { result ->
                if (result.payload?.isEmpty() == true) {
                    ResultWrapper.Empty(result.payload)
                } else {
                    result
                }
            }.catch { exception ->
                emit(ResultWrapper.Error(exception = Exception(exception)))
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(500)
            }
    }

    override fun insertListTransaction(transactions: List<TransactionEntity>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val result = transactionDao.insertListTransaction(transactions)
            result.any { it > 0 }
        }
    }

    override fun insertTransaction(transactionEntity: TransactionEntity): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { transactionDao.insertTransaction(transactionEntity) > 0 }
    }

    override suspend fun deleteTransaction(id: Int) {
        return transactionDao.deleteTransaction(id)
    }

    override suspend fun deleteAllTransaction() {
        return transactionDao.deleteAllTransaction()
    }

}
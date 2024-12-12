package com.aeryz.seimbanginapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aeryz.seimbanginapp.data.local.database.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` WHERE type = :type ORDER BY created_at DESC")
    fun getTransactionByType(type: Int): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListTransaction(transactions: List<TransactionEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Query("DELETE FROM `transaction` WHERE id = :id")
    suspend fun deleteTransaction(id: Int)

    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransaction()
}

package com.aeryz.seimbanginapp.data.local.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aeryz.seimbanginapp.model.TransactionItem
import kotlinx.parcelize.Parcelize

@Entity("transaction")
@Parcelize
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int? = 0,
    @ColumnInfo(name = "type")
    val type: Int? = 0,
    @ColumnInfo(name = "category")
    val category: String? = "",
    @ColumnInfo(name = "amount")
    val amount: String? = "",
    @ColumnInfo(name = "description")
    val description: String? = "",
    @ColumnInfo(name = "created_at")
    val createdAt: String? = "",
    @ColumnInfo(name = "update_at")
    val updatedAt: String? = ""
) : Parcelable


fun TransactionEntity.toTransactionItem() = TransactionItem(
    id = this.id ?: 0,
    userId = this.userId ?: 0,
    type = this.type ?: 0,
    category = this.category.orEmpty(),
    amount = this.amount.orEmpty(),
    description = this.description.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty()
)

fun Collection<TransactionEntity>.toTransactionItemList() = this.map { it.toTransactionItem() }
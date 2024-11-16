package com.aeryz.seimbanginapp.data.network.model.transactionHistory


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.aeryz.seimbanginapp.model.TransactionItem

@Keep
data class TransactionData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun TransactionData.toTransactionItem() = TransactionItem(
    id = this.id ?: 0,
    userId = this.userId ?: 0,
    type = this.type ?: 0,
    category = this.category.orEmpty(),
    amount = this.amount.orEmpty(),
    description = this.description.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty()
)

fun Collection<TransactionData>.toTransactionItemList() = this.map { it.toTransactionItem() }
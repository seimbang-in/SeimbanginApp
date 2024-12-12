package com.aeryz.seimbanginapp.data.network.model.transactionHistory

import androidx.annotation.Keep
import com.aeryz.seimbanginapp.model.TransactionItem
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("name")
    val name: String?,
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
    val updatedAt: String?,
    @SerializedName("items")
    val items: List<ProductData>?
)

fun TransactionData.toTransactionItem() = TransactionItem(
    id = this.id ?: 0,
    userId = this.userId ?: 0,
    name = this.name.orEmpty(),
    type = this.type ?: 0,
    category = this.category.orEmpty(),
    amount = this.amount.orEmpty(),
    description = this.description.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    items = this.items?.toProductItemList().orEmpty()
)

fun Collection<TransactionData>.toTransactionItemList() = this.map { it.toTransactionItem() }

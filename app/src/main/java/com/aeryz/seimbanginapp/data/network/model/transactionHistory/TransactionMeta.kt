package com.aeryz.seimbanginapp.data.network.model.transactionHistory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionMeta(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("totalItems")
    val totalItems: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?,
    @SerializedName("hasNextPage")
    val hasNextPage: Boolean?,
    @SerializedName("hasPreviousPage")
    val hasPreviousPage: Boolean?
)

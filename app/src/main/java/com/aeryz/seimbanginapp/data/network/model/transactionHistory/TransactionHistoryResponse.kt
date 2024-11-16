package com.aeryz.seimbanginapp.data.network.model.transactionHistory


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TransactionHistoryResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<TransactionData>?,
    @SerializedName("meta")
    val meta: TransactionMeta?
)
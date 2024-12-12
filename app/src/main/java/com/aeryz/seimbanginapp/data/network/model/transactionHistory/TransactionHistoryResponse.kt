package com.aeryz.seimbanginapp.data.network.model.transactionHistory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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

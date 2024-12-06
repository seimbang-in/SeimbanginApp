package com.aeryz.seimbanginapp.data.network.model.createTransaction


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CreateTransactionRequest(
    @SerializedName("type")
    val type: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("items")
    val items: List<TransactionItemRequest>?
)
package com.aeryz.seimbanginapp.data.network.model.createTransaction


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CreateTransactionResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val createTransactionData: CreateTransactionData?
)
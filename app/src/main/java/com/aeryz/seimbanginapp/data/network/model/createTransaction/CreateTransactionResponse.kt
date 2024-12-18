package com.aeryz.seimbanginapp.data.network.model.createTransaction

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateTransactionResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val createTransactionData: CreateTransactionData?
)

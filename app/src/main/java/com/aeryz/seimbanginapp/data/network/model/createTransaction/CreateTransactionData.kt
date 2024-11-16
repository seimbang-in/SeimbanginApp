package com.aeryz.seimbanginapp.data.network.model.createTransaction


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CreateTransactionData(
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("balance")
    val balance: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("description")
    val description: String?
)
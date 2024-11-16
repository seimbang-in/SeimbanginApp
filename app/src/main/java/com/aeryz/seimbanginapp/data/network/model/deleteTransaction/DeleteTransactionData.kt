package com.aeryz.seimbanginapp.data.network.model.deleteTransaction


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DeleteTransactionData(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("balance")
    val balance: String?
)
package com.aeryz.seimbanginapp.data.network.model.deleteTransaction

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteTransactionData(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("balance")
    val balance: String?
)

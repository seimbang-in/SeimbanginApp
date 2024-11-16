package com.aeryz.seimbanginapp.data.network.model.createTransaction

import com.google.gson.annotations.SerializedName

data class CreateTransactionRequest(
    @SerializedName("type")
    val type : Int?,
    @SerializedName("category")
    val category : String?,
    @SerializedName("amount")
    val amount : Double?,
    @SerializedName("description")
    val description : String?
)

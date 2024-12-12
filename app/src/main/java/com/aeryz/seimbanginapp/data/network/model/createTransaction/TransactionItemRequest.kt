package com.aeryz.seimbanginapp.data.network.model.createTransaction

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionItemRequest(
    @SerializedName("item_name")
    var itemName: String? = "",
    @SerializedName("category")
    var category: String? = "category",
    @SerializedName("price")
    var price: Int = 1,
    @SerializedName("quantity")
    var quantity: Int = 1
)

package com.aeryz.seimbanginapp.data.network.model.createTransaction


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TransactionItemRequest(
    @SerializedName("item_name")
    var itemName: String? = "",
    @SerializedName("category")
    var category: String? = "others",
    @SerializedName("price")
    var price: Int = 1,
    @SerializedName("quantity")
    var quantity: Int = 1
)
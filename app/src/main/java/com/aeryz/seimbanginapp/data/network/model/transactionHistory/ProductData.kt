package com.aeryz.seimbanginapp.data.network.model.transactionHistory

import androidx.annotation.Keep
import com.aeryz.seimbanginapp.model.ProductItem
import com.google.gson.annotations.SerializedName

@Keep
data class ProductData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("transaction_id")
    val transactionId: Int?,
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun ProductData.toProductItem() = ProductItem(
    id = this.id ?: 0,
    transactionId = this.transactionId ?: 0,
    itemName = this.itemName.orEmpty(),
    category = this.category.orEmpty(),
    price = this.price.orEmpty(),
    quantity = this.quantity ?: 0,
    subtotal = this.subtotal.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty()
)

fun Collection<ProductData>.toProductItemList() = this.map { it.toProductItem() }

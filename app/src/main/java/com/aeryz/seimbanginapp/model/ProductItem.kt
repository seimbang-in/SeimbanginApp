package com.aeryz.seimbanginapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    val id: Int?,
    val transactionId: Int?,
    val itemName: String?,
    val category: String?,
    val price: String?,
    val quantity: Int?,
    val subtotal: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable

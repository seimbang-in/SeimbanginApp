package com.aeryz.seimbanginapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionItem(
    val id: Int?,
    val userId: Int?,
    val name: String?,
    val type: Int?,
    val category: String?,
    val amount: String?,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val items: List<ProductItem>?
) : Parcelable

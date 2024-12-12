package com.aeryz.seimbanginapp.data.network.model.ocr

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class OcrProduct(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("subtotal")
    val subtotal: Int?
) : Parcelable

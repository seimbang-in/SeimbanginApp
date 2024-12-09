package com.aeryz.seimbanginapp.data.network.model.ocr


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class OcrProduct(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("category")
    val category: String?
) : Parcelable
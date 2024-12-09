package com.aeryz.seimbanginapp.data.network.model.ocr


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class OcrData(
    @SerializedName("products")
    val products: List<OcrProduct>?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("total")
    val total: String?
) : Parcelable
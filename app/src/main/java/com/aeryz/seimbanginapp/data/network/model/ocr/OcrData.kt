package com.aeryz.seimbanginapp.data.network.model.ocr

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class OcrData(
    @SerializedName("items")
    val items: List<OcrProduct>?
) : Parcelable

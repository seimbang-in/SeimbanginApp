package com.aeryz.seimbanginapp.data.network.model.uploadProfileImage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("url")
    val url: String?
)
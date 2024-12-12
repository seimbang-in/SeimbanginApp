package com.aeryz.seimbanginapp.data.network.model.uploadProfileImage

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("url")
    val url: String?
)

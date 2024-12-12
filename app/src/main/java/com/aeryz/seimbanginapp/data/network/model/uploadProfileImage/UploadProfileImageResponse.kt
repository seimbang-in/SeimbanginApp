package com.aeryz.seimbanginapp.data.network.model.uploadProfileImage

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UploadProfileImageResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val `data`: Data?
)

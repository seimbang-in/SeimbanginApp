package com.aeryz.seimbanginapp.data.network.model.uploadProfileImage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UploadProfileImageResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val `data`: Data?
)
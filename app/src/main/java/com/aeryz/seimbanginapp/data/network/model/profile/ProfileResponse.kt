package com.aeryz.seimbanginapp.data.network.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val profileData: ProfileData?
)
package com.aeryz.seimbanginapp.data.network.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val profileData: ProfileData?
)

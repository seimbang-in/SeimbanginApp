package com.aeryz.seimbanginapp.data.network.model.editProfile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EditProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val editProfileData: EditProfileData?
)

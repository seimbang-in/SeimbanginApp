package com.aeryz.seimbanginapp.data.network.model.editProfile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EditProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val editProfileData: EditProfileData?
)
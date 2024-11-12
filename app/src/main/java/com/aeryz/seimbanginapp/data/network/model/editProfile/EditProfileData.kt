package com.aeryz.seimbanginapp.data.network.model.editProfile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EditProfileData(
    @SerializedName("full_name")
    val fullName: String?
)
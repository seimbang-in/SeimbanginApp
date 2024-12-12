package com.aeryz.seimbanginapp.data.network.model.editProfile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EditProfileData(
    @SerializedName("full_name")
    val fullName: String?
)

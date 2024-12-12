package com.aeryz.seimbanginapp.data.network.model.editProfile

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("full_name")
    val fullName: String?
)

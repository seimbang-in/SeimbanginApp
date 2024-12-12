package com.aeryz.seimbanginapp.data.network.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("username")
    val userName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)

package com.aeryz.seimbanginapp.data.network.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("identifier")
    val identifier: String?,
    @SerializedName("password")
    val password: String?
)

package com.aeryz.seimbanginapp.data.network.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginData(
    @SerializedName("token")
    val token: String?,
    @SerializedName("expiresIn")
    val expiresIn: Int?
)

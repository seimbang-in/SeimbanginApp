package com.aeryz.seimbanginapp.data.network.model.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginData(
    @SerializedName("token")
    val token: String?,
    @SerializedName("expiresIn")
    val expiresIn: Int?
)
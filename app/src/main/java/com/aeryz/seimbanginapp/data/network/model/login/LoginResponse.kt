package com.aeryz.seimbanginapp.data.network.model.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val loginData: LoginData?
)
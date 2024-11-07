package com.aeryz.seimbanginapp.data.network.model.register


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RegisterResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)
package com.aeryz.seimbanginapp.data.network.model.common

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)

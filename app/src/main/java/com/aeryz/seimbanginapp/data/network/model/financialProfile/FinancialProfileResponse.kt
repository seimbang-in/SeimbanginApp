package com.aeryz.seimbanginapp.data.network.model.financialProfile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FinancialProfileResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val financialProfileData: FinancialProfileData?
)

package com.aeryz.seimbanginapp.data.network.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("balance")
    val balance: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("profilePicture")
    val profilePicture: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("finance_profile")
    val financeProfile: FinanceProfile?
)

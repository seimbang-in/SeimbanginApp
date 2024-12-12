package com.aeryz.seimbanginapp.data.network.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class FinanceProfile(
    @SerializedName("monthly_income")
    val monthlyIncome: String?,
    @SerializedName("current_savings")
    val currentSavings: String?,
    @SerializedName("debt")
    val debt: String?,
    @SerializedName("financial_goals")
    val financialGoals: String?,
    @SerializedName("risk_management")
    val riskManagement: String?
) : Parcelable

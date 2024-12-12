package com.aeryz.seimbanginapp.data.network.model.advisor

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AdvisorData(
    @SerializedName("income")
    val income: Int?,
    @SerializedName("outcome")
    val outcome: Int?,
    @SerializedName("debt")
    val debt: Int?,
    @SerializedName("current_savings")
    val currentSavings: Int?,
    @SerializedName("financial_goals")
    val financialGoals: String?,
    @SerializedName("risk_management")
    val riskManagement: String?,
    @SerializedName("market_conditions")
    val marketConditions: String?,
    @SerializedName("advice")
    val advice: String?
)

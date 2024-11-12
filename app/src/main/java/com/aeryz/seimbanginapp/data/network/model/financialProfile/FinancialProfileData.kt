package com.aeryz.seimbanginapp.data.network.model.financialProfile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FinancialProfileData(
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
)
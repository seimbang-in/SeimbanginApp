package com.aeryz.seimbanginapp.data.network.model.financialProfile

import com.google.gson.annotations.SerializedName

data class FinancialProfileRequest(
    @SerializedName("monthly_income")
    val monthlyIncome: Double?,
    @SerializedName("current_savings")
    val currentSavings: Double?,
    @SerializedName("debt")
    val debt: Double?,
    @SerializedName("financial_goals")
    val financialGoals: String?,
    @SerializedName("risk_management")
    val riskManagement: String?
)
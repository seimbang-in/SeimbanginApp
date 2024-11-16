package com.aeryz.seimbanginapp.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun capitalizeFirstChar(text: String?): String {
    return text?.replaceFirstChar { it.uppercase() } ?: ""
}

fun formatAmount(amount: String?, type: Int?): String {
    if (amount.isNullOrBlank() || type == null) return ""
    val formattedAmount = try {
        NumberFormat.getNumberInstance(Locale.getDefault()).format(amount.toDouble())
    } catch (e: NumberFormatException) {
        return ""
    }
    return when (type) {
        0 -> "+ Rp $formattedAmount"
        else -> "- Rp $formattedAmount"
    }
}

fun withDateFormat(inputDate: String): String {
    val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = oldFormat.parse(inputDate)
    val newFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm:ss", Locale.getDefault())
    return newFormat.format(date ?: "")
}
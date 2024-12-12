package com.aeryz.seimbanginapp.data.local.datasource

import android.content.Context
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.model.TransactionCategory

class TransactionCategoryDataSource(private val context: Context) {
    fun getCategories(): List<TransactionCategory> {
        return listOf(
            TransactionCategory("Category", "category", R.drawable.ic_question_mark_24),
            TransactionCategory(context.getString(R.string.text_food), "food", R.drawable.ic_food_24),
            TransactionCategory(context.getString(R.string.text_transportation), "transportation", R.drawable.ic_transportation_24),
            TransactionCategory(context.getString(R.string.text_utilities), "utilities", R.drawable.ic_utilities_24),
            TransactionCategory(context.getString(R.string.text_entertainment), "entertainment", R.drawable.ic_entertainment_24),
            TransactionCategory(context.getString(R.string.text_shopping), "shopping", R.drawable.ic_shopping_24),
            TransactionCategory(context.getString(R.string.text_healthcare), "healthcare", R.drawable.ic_healthcare_24),
            TransactionCategory(context.getString(R.string.text_education), "education", R.drawable.ic_education_24),
            TransactionCategory(context.getString(R.string.text_others), "others", R.drawable.ic_others_24)
        )
    }
}

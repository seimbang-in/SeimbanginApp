package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.model.TransactionCategory

class CategorySpinnerAdapter(
    private val context: Context,
    private val categories: List<TransactionCategory>
) : android.widget.BaseAdapter(), SpinnerAdapter {

    override fun getCount(): Int = categories.size

    override fun getItem(position: Int): Any = categories[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_item_transaction_category, parent, false)

        val category = categories[position]
        val iconView = view.findViewById<ImageView>(R.id.categoryIcon)
        val nameView = view.findViewById<TextView>(R.id.categoryName)

        iconView.setImageDrawable(ContextCompat.getDrawable(context, category.iconResId))
        nameView.text = category.name

        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}

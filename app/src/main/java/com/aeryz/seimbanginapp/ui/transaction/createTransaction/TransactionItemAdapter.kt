package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.seimbanginapp.data.local.datasource.TransactionCategoryDataSource
import com.aeryz.seimbanginapp.data.network.model.createTransaction.TransactionItemRequest
import com.aeryz.seimbanginapp.databinding.LayoutFormItemTransactionBinding

class TransactionItemAdapter(
    private val items: MutableList<TransactionItemRequest>,
    private val listener: OnTransactionItemChangeListener
) : RecyclerView.Adapter<TransactionItemAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LayoutFormItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionItemRequest, position: Int) {
            with(binding) {
                tvItem.text = "Item - ${position + 1}"
                etName.setText(item.itemName)
                etPrice.setText(item.price.toString())
                etQuantity.setText(item.quantity.toString())

                val categories = TransactionCategoryDataSource(itemView.context).getCategories()
                val categoryAdapter = CategorySpinnerAdapter(itemView.context, categories)
                spinnerCategory.adapter = categoryAdapter

                val selectedCategoryPosition = categories.indexOfFirst { it.value == item.category }
                spinnerCategory.setSelection(if (selectedCategoryPosition >= 0) selectedCategoryPosition else 0)

                spinnerCategory.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (position == 0) {
                                item.category = null
                            } else {
                                item.category = categories[position].value
                            }
                            listener.onTransactionItemChanged(items)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }

                etName.doOnTextChanged { text, _, _, _ ->
                    item.itemName = text.toString()
                    if (item.itemName.isNullOrBlank()) {
                        binding.tilName.error = "Item name cannot be empty"
                    } else {
                        binding.tilName.error = null
                    }
                    listener.onTransactionItemChanged(items)
                }
                etPrice.doOnTextChanged { text, _, _, _ ->
                    item.price = text.toString().toIntOrNull() ?: 0
                    if (item.price <= 0) {
                        binding.tilPrice.error = "Price must be greater than 0"
                    } else {
                        binding.tilPrice.error = null
                    }
                    listener.onTransactionItemChanged(items)
                }
                etQuantity.doOnTextChanged { text, _, _, _ ->
                    item.quantity = text.toString().toIntOrNull() ?: 0
                    if (item.quantity <= 0) {
                        binding.tilQuantity.error = "Quantity must be greater than 0"
                    } else {
                        binding.tilQuantity.error = null
                    }
                    listener.onTransactionItemChanged(items)
                }
                icDelete.apply {
                    visibility = if (position == 0) View.GONE else View.VISIBLE
                    setOnClickListener {
                        if (position > 0) {
                            removeItem(position)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutFormItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun removeItem(position: Int) {
        if (position > 0) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            listener.onTransactionItemChanged(items)
        }
    }

    fun validateItems(): Boolean {
        var isValid = true
        for ((index, item) in items.withIndex()) {
            if (item.itemName.isNullOrBlank() || item.price <= 0 || item.quantity <= 0 || item.category == null) {
                isValid = false
                notifyItemChanged(index)
            }
        }
        return isValid
    }
}

interface OnTransactionItemChangeListener {
    fun onTransactionItemChanged(transactionItems: List<TransactionItemRequest>)
}

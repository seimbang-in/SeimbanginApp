package com.aeryz.seimbanginapp.ui.transaction.transactionHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aeryz.seimbanginapp.data.local.datasource.TransactionCategoryDataSource
import com.aeryz.seimbanginapp.databinding.LayoutItemTransactionListBinding
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.utils.formatAmount

class TransactionListAdapter(
    private val itemClick: (TransactionItem) -> Unit
) : RecyclerView.Adapter<TransactionListViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<TransactionItem>() {
            override fun areItemsTheSame(
                oldItem: TransactionItem,
                newItem: TransactionItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionItem,
                newItem: TransactionItem
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding = LayoutItemTransactionListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionListViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun submitData(data: List<TransactionItem>) {
        dataDiffer.submitList(data)
    }
}

class TransactionListViewHolder(
    private val binding: LayoutItemTransactionListBinding,
    val itemClick: (TransactionItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TransactionItem) {
        val context = itemView.context
        val categoriesData = TransactionCategoryDataSource(context).getCategories()
        val sameItem = categoriesData.find { it.value == item.category }
        val iconRes = sameItem?.iconResId
        binding.ivCategory.load(iconRes)
        binding.tvCategory.text = sameItem?.name
        binding.tvDescription.text = item.description
        binding.tvAmount.text = formatAmount(item.amount, item.type)
        itemView.setOnClickListener {
            itemClick(item)
        }
    }
}

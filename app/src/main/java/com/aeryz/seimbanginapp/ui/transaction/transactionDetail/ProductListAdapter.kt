package com.aeryz.seimbanginapp.ui.transaction.transactionDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.local.datasource.TransactionCategoryDataSource
import com.aeryz.seimbanginapp.databinding.LayoutProductItemListBinding
import com.aeryz.seimbanginapp.model.ProductItem
import com.aeryz.seimbanginapp.utils.withCurrencyFormat

class ProductListAdapter() : RecyclerView.Adapter<ProductListViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = LayoutProductItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductListViewHolder(binding)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun submitData(data: List<ProductItem>) {
        dataDiffer.submitList(data)
    }
}

class ProductListViewHolder(private val binding: LayoutProductItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductItem) {
        val context = itemView.context
        val categoriesData = TransactionCategoryDataSource(context).getCategories()
        val sameItem = categoriesData.find { it.value == item.category }
        val iconRes = sameItem?.iconResId
        binding.ivCategory.load(iconRes)
        binding.tvName.text = item.itemName
        binding.tvPrice.text = withCurrencyFormat(item.price)
        binding.tvQuantity.text =
            context.getString(R.string.text_format_quantity, item.quantity.toString())
    }
}

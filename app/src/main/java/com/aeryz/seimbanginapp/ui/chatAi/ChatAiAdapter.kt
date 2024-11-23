package com.aeryz.seimbanginapp.ui.chatAi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.LayoutChatAiItemBinding
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.asTextOrNull

class ChatAiAdapter() : RecyclerView.Adapter<ChatAiViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(
                oldItem: Content,
                newItem: Content
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Content,
                newItem: Content
            ): Boolean {
                return oldItem.role == newItem.role
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAiViewHolder {
        val binding = LayoutChatAiItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatAiViewHolder(binding)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ChatAiViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun submitData(data: List<Content>) {
        dataDiffer.submitList(data)
    }
}

class ChatAiViewHolder(
    private val binding: LayoutChatAiItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Content) {
        binding.tvChatText.text = item.parts.first().asTextOrNull()
        if (item.role == "user") {
            binding.tvChatText.setBackgroundResource(R.drawable.bg_chat_role_user)
            with(binding) {
                ConstraintSet().apply {
                    clone(root)
                    connect(tvChatText.id, ConstraintSet.END, root.id, ConstraintSet.END)
                    clear(tvChatText.id, ConstraintSet.START)
                    applyTo(root)
                }
            }
        } else {
            binding.tvChatText.setBackgroundResource(R.drawable.bg_chat_role_model)
            with(binding) {
                ConstraintSet().apply {
                    clone(root)
                    connect(tvChatText.id, ConstraintSet.START, root.id, ConstraintSet.START)
                    clear(tvChatText.id, ConstraintSet.END)
                    applyTo(root)
                }
            }
        }
    }
}

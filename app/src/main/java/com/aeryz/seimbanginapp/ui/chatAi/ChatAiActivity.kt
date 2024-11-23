package com.aeryz.seimbanginapp.ui.chatAi

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityChatAiBinding
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.google.ai.client.generativeai.type.Content
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatAiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatAiBinding

    private val viewModel: ChatAiViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatAiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        setupAction()
        observeChatList()
    }

    private fun observeChatList() {
        viewModel.chatResult.observe(this) { result ->
            result.proceedWhen(
                doOnLoading = {
                    showLoading(true)
                    binding.rvChatList.isVisible = true
                    binding.tvDescription.isVisible = false
                },
                doOnSuccess = {
                    showLoading(false)
                    binding.rvChatList.isVisible = true
                    binding.tvDescription.isVisible = false
                    it.payload?.let { it1 -> setupRecyclerView(it1) }
                },
                doOnError = {
                    showLoading(false)
                    binding.rvChatList.isVisible = false
                    binding.tvDescription.isVisible = true
                    binding.tvDescription.text = it.exception?.localizedMessage
                },
                doOnEmpty = {
                    showLoading(false)
                    binding.rvChatList.isVisible = false
                    binding.tvDescription.isVisible = true
                    binding.tvDescription.text = getString(R.string.text_ask_anything_to_ai)
                }
            )
        }
    }

    private fun setupRecyclerView(data: List<Content>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvChatList.layoutManager = layoutManager
        val adapter = ChatAiAdapter()
        binding.rvChatList.adapter = adapter
        adapter.submitData(data)
        binding.rvChatList.scrollToPosition(data.size - 1)
    }

    private fun showLoading(state: Boolean) {
        binding.pbLoading.isVisible = state
    }

    private fun setupAction() {
        binding.btnSendButton.setOnClickListener {
            sendPrompt()
        }
    }

    private fun sendPrompt() {
        val prompt = binding.etPromptBar.text.toString().trim()
        if (prompt.isNotEmpty()) {
            viewModel.sendChat(prompt)
            binding.etPromptBar.text.clear()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.etPromptBar.windowToken, 0)
        }
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteChatHistory()
    }
}
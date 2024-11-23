package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.BuildConfig
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceedFlow
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow

class ChatAiRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API
    )
    private val chatHistory = mutableListOf<Content>()
    private val chat = generativeModel.startChat(chatHistory)

    fun sendPrompt(prompt: String): Flow<ResultWrapper<List<Content>>> {
        val result = proceedFlow {
            val userContent = content(role = "user") { text(prompt) }
            chatHistory.add(userContent)
            val response = chat.sendMessage(prompt)
            val responseText = response.text ?: throw Exception("Response text is null")
            val modelContent = content(role = "model") { text(responseText) }
            chatHistory.add(modelContent)
            chatHistory.toList()
        }
        return result
    }

    fun deleteChatHistory() {
        chatHistory.clear()
    }
}
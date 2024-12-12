package com.aeryz.seimbanginapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aeryz.seimbanginapp.data.repository.LocalTransactionRepository

class DashboardViewModel(private val localTransactionRepository: LocalTransactionRepository) : ViewModel() {

    fun getTransactionByType(type: Int) = localTransactionRepository.getTransactionByType(type).asLiveData()
}

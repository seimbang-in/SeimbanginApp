package com.aeryz.seimbanginapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun doLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.deleteToken()
        }
    }
}
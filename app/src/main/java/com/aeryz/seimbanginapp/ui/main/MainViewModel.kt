package com.aeryz.seimbanginapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.launch

class MainViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {

    val userDarkModeLiveData = userPreferenceDataSource.getUserDarkModePrefFlow().asLiveData()

    fun setUserDarkModePref(isUsingDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserDarkModePref(isUsingDarkMode)
        }
    }
}

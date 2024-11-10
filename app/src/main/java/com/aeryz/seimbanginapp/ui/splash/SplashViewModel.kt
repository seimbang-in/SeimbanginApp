package com.aeryz.seimbanginapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _isFirstTime = MutableLiveData<Boolean>()
    val isFirstTime: LiveData<Boolean>
        get() = _isFirstTime

    private val _isUserLoggedIn = MutableLiveData<String>()
    val isUserLoggedIn: LiveData<String>
        get() = _isUserLoggedIn

    val userDarkMode = userPreferenceDataSource.getUserDarkModePrefFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            userPreferenceDataSource.getUserTokenFlow().collect {
                _isUserLoggedIn.postValue(it)
            }
        }
    }
}
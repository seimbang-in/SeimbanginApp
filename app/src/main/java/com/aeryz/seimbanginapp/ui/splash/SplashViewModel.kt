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

    private val _tokenExpiresTime = MutableLiveData<Long>()
    val tokenExpiresTime: LiveData<Long>
        get() = _tokenExpiresTime

    private val _isUserLoggedIn = MutableLiveData<String>()
    val isUserLoggedIn: LiveData<String>
        get() = _isUserLoggedIn

    val userDarkMode = userPreferenceDataSource.getUserDarkModePrefFlow()

    init {
        getUserToken()
        getTokenExpiresTime()
    }

    private fun getUserToken() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            userPreferenceDataSource.getUserTokenFlow().collect {
                _isUserLoggedIn.postValue(it)
            }
        }
    }

    private fun getTokenExpiresTime() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.getTokenExpires().collect {
                _tokenExpiresTime.postValue(it)
            }
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.deleteToken()
        }
    }

    fun deleteTokenExpires() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.deleteTokenExpires()
        }
    }
}
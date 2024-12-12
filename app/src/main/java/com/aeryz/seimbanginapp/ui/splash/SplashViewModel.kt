package com.aeryz.seimbanginapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datasource.IntroPageDataSource
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val introPageDataSource: IntroPageDataSource
) : ViewModel() {

    private val _tokenExpiresTime = MutableLiveData<Long>()
    val tokenExpiresTime: LiveData<Long>
        get() = _tokenExpiresTime

    private val _isUserLoggedIn = MutableLiveData<String>()
    val isUserLoggedIn: LiveData<String>
        get() = _isUserLoggedIn

    val userDarkMode = userPreferenceDataSource.getUserDarkModePrefFlow()

    init {
        getShouldShowIntroPage()
        getUserToken()
        getTokenExpiresTime()
    }

    private val _isFirstTime = MutableLiveData<Boolean>()
    val isFirstTime: LiveData<Boolean> get() = _isFirstTime

    fun getIntroPageData() = introPageDataSource.getIntroPageData()

    fun setShouldShowIntroPage(isFirstTime: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.setShouldShowIntroPage(isFirstTime)
        }
    }

    private fun getShouldShowIntroPage() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            userPreferenceDataSource.getShouldShowIntroPage().collect {
                _isFirstTime.postValue(it)
            }
        }
    }

    private fun getUserToken() {
        viewModelScope.launch(Dispatchers.IO) {
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

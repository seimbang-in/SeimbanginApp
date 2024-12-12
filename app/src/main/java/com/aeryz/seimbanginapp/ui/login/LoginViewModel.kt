package com.aeryz.seimbanginapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _userToken = MutableLiveData<String?>()
    val userToken: LiveData<String?>
        get() = _userToken

    private val _loginResult = MutableLiveData<ResultWrapper<LoginResponse>>()
    val loginResult: LiveData<ResultWrapper<LoginResponse>>
        get() = _loginResult

    fun login(identifier: String, password: String) {
        viewModelScope.launch {
            repository.login(identifier, password).collect {
                _loginResult.postValue(it)
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userPreferenceDataSource.saveUserToken(token)
        }
    }

    fun saveTokenExpiresTime(expiresTime: Long) {
        viewModelScope.launch {
            userPreferenceDataSource.saveTokenExpires(expiresTime)
        }
    }

    init {
        viewModelScope.launch {
            userPreferenceDataSource.getUserTokenFlow()
        }
    }
}

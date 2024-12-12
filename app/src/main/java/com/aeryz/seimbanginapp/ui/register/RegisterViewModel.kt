package com.aeryz.seimbanginapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeryz.seimbanginapp.data.network.model.register.RegisterResponse
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ResultWrapper<RegisterResponse>>()
    val registerResult: LiveData<ResultWrapper<RegisterResponse>>
        get() = _registerResult

    fun register(
        fullName: String,
        userName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            repository.register(fullName, userName, email, password).collect {
                _registerResult.postValue(it)
            }
        }
    }
}

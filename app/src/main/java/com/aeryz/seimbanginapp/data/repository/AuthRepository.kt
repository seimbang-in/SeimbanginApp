package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSource
import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.network.model.register.RegisterRequest
import com.aeryz.seimbanginapp.data.network.model.register.RegisterResponse
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<ResultWrapper<LoginResponse>>
    fun register(
        fullName: String,
        userName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<RegisterResponse>>
}

class AuthRepositoryImpl(private val dataSource: SeimbanginDataSource) : AuthRepository {
    override fun login(email: String, password: String): Flow<ResultWrapper<LoginResponse>> {
        return proceedFlow {
            dataSource.login(LoginRequest(email, password))
        }
    }

    override fun register(
        fullName: String,
        userName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<RegisterResponse>> {
        return proceedFlow {
            dataSource.register(RegisterRequest(fullName, userName, email, password))
        }
    }

}
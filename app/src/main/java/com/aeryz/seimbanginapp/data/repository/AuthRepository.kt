package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSource
import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<ResultWrapper<LoginResponse>>
}

class AuthRepositoryImpl(private val dataSource: SeimbanginDataSource) : AuthRepository {
    override fun login(email: String, password: String): Flow<ResultWrapper<LoginResponse>> {
        return proceedFlow {
            dataSource.login(LoginRequest(email, password))
        }
    }

}
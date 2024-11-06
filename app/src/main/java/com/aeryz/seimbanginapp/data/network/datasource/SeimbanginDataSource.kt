package com.aeryz.seimbanginapp.data.network.datasource

import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.network.service.ApiService

interface SeimbanginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}

class SeimbanginDataSourceImpl(private val service: ApiService) : SeimbanginDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return service.login(loginRequest)
    }

}
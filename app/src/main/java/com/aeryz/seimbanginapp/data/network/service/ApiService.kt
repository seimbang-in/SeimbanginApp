package com.aeryz.seimbanginapp.data.network.service

import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Login
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
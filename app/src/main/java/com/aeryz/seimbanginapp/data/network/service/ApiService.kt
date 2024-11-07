package com.aeryz.seimbanginapp.data.network.service

import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.network.model.register.RegisterRequest
import com.aeryz.seimbanginapp.data.network.model.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Login
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // Register
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
}
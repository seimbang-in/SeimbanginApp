package com.aeryz.seimbanginapp.data.network.service

import com.aeryz.seimbanginapp.data.network.model.editProfile.EditProfileRequest
import com.aeryz.seimbanginapp.data.network.model.editProfile.EditProfileResponse
import com.aeryz.seimbanginapp.data.network.model.financialProfile.FinancialProfileRequest
import com.aeryz.seimbanginapp.data.network.model.financialProfile.FinancialProfileResponse
import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.network.model.register.RegisterRequest
import com.aeryz.seimbanginapp.data.network.model.register.RegisterResponse
import com.aeryz.seimbanginapp.data.network.model.uploadProfileImage.UploadProfileImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    // Login
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // Register
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    // Profile
    @Headers("Content-Type: multipart/form-data")
    @GET("user/profile")
    suspend fun getUserProfile(): ProfileResponse

    // Update Financial Profile
    @PUT("financial-profile")
    suspend fun updateFinancialProfile(@Body financialProfileRequest: FinancialProfileRequest): FinancialProfileResponse

    // Edit Profile
    @PUT("user")
    suspend fun editProfile(@Body editProfileRequest: EditProfileRequest): EditProfileResponse

    // Upload Profile Image
    @Multipart
    @POST("user/upload-pfp")
    suspend fun uploadProfileImage(@Part image: MultipartBody.Part): UploadProfileImageResponse
}
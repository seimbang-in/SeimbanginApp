package com.aeryz.seimbanginapp.data.repository

import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSource
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
import com.aeryz.seimbanginapp.utils.ResultWrapper
import com.aeryz.seimbanginapp.utils.proceedFlow
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AuthRepository {
    fun login(email: String, password: String): Flow<ResultWrapper<LoginResponse>>
    fun register(
        fullName: String,
        userName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<RegisterResponse>>
    fun getUserProfile(): Flow<ResultWrapper<ProfileResponse>>
    fun updateFinancialProfile(
        monthlyIncome: Double?,
        currentSavings: Double?,
        debt: Double?,
        financialGoals: String?,
        riskManagement: String?
    ): Flow<ResultWrapper<FinancialProfileResponse>>
    fun editProfile(fullName: String): Flow<ResultWrapper<EditProfileResponse>>
    fun uploadProfileImage(image: MultipartBody.Part): Flow<ResultWrapper<UploadProfileImageResponse>>
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

    override fun getUserProfile(): Flow<ResultWrapper<ProfileResponse>> {
        return proceedFlow {
            dataSource.getUserProfile()
        }
    }

    override fun updateFinancialProfile(
        monthlyIncome: Double?,
        currentSavings: Double?,
        debt: Double?,
        financialGoals: String?,
        riskManagement: String?
    ): Flow<ResultWrapper<FinancialProfileResponse>> {
        return proceedFlow {
            dataSource.updateFinancialProfile(
                FinancialProfileRequest(
                    monthlyIncome, currentSavings, debt, financialGoals, riskManagement
                )
            )
        }
    }

    override fun editProfile(fullName: String): Flow<ResultWrapper<EditProfileResponse>> {
        return proceedFlow {
            dataSource.editProfile(EditProfileRequest(fullName))
        }
    }

    override fun uploadProfileImage(image: MultipartBody.Part): Flow<ResultWrapper<UploadProfileImageResponse>> {
        return proceedFlow {
            dataSource.uploadProfileImage(image)
        }
    }

}
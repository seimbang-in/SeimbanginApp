package com.aeryz.seimbanginapp.data.network.datasource

import com.aeryz.seimbanginapp.data.network.model.advisor.AdvisorResponse
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionRequest
import com.aeryz.seimbanginapp.data.network.model.createTransaction.CreateTransactionResponse
import com.aeryz.seimbanginapp.data.network.model.deleteTransaction.DeleteTransactionResponse
import com.aeryz.seimbanginapp.data.network.model.editProfile.EditProfileRequest
import com.aeryz.seimbanginapp.data.network.model.editProfile.EditProfileResponse
import com.aeryz.seimbanginapp.data.network.model.financialProfile.FinancialProfileRequest
import com.aeryz.seimbanginapp.data.network.model.financialProfile.FinancialProfileResponse
import com.aeryz.seimbanginapp.data.network.model.login.LoginRequest
import com.aeryz.seimbanginapp.data.network.model.login.LoginResponse
import com.aeryz.seimbanginapp.data.network.model.ocr.OcrResponse
import com.aeryz.seimbanginapp.data.network.model.profile.ProfileResponse
import com.aeryz.seimbanginapp.data.network.model.register.RegisterRequest
import com.aeryz.seimbanginapp.data.network.model.register.RegisterResponse
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.TransactionHistoryResponse
import com.aeryz.seimbanginapp.data.network.model.uploadProfileImage.UploadProfileImageResponse
import com.aeryz.seimbanginapp.data.network.service.ApiService
import okhttp3.MultipartBody

interface SeimbanginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
    suspend fun getUserProfile(): ProfileResponse
    suspend fun updateFinancialProfile(financialProfileRequest: FinancialProfileRequest): FinancialProfileResponse
    suspend fun editProfile(editProfileRequest: EditProfileRequest): EditProfileResponse
    suspend fun uploadProfileImage(image: MultipartBody.Part): UploadProfileImageResponse
    suspend fun createTransaction(createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse
    suspend fun getTransactionHistory(limit: Int?, page: Int?): TransactionHistoryResponse
    suspend fun deleteTransaction(id: Int?): DeleteTransactionResponse
    suspend fun scanReceipt(image: MultipartBody.Part): OcrResponse
    suspend fun getAdvice(): AdvisorResponse
}

class SeimbanginDataSourceImpl(private val service: ApiService) : SeimbanginDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return service.login(loginRequest)
    }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return service.register(registerRequest)
    }

    override suspend fun getUserProfile(): ProfileResponse {
        return service.getUserProfile()
    }

    override suspend fun updateFinancialProfile(financialProfileRequest: FinancialProfileRequest): FinancialProfileResponse {
        return service.updateFinancialProfile(financialProfileRequest)
    }

    override suspend fun editProfile(editProfileRequest: EditProfileRequest): EditProfileResponse {
        return service.editProfile(editProfileRequest)
    }

    override suspend fun uploadProfileImage(image: MultipartBody.Part): UploadProfileImageResponse {
        return service.uploadProfileImage(image)
    }

    override suspend fun createTransaction(createTransactionRequest: CreateTransactionRequest): CreateTransactionResponse {
        return service.createTransaction(createTransactionRequest)
    }

    override suspend fun getTransactionHistory(limit: Int?, page: Int?): TransactionHistoryResponse {
        return service.getTransactionHistory(limit, page)
    }

    override suspend fun deleteTransaction(id: Int?): DeleteTransactionResponse {
        return service.deleteTransaction(id)
    }

    override suspend fun scanReceipt(image: MultipartBody.Part): OcrResponse {
        return service.scanReceipt(image)
    }

    override suspend fun getAdvice(): AdvisorResponse {
        return service.getAdvice()
    }
}

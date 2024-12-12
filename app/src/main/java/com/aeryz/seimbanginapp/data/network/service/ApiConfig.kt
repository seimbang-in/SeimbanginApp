package com.aeryz.seimbanginapp.data.network.service

import com.aeryz.seimbanginapp.BuildConfig
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(
            chuckerInterceptor: ChuckerInterceptor,
            userPreferenceDataSource: UserPreferenceDataSource,
        ): ApiService {
            val client =
                OkHttpClient
                    .Builder()
                    .addInterceptor(chuckerInterceptor)
                    .addInterceptor(AuthInterceptor(userPreferenceDataSource))
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit
                    .Builder()
                    .baseUrl(BuildConfig.BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

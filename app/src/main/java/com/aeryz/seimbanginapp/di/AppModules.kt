package com.aeryz.seimbanginapp.di

import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSource
import com.aeryz.seimbanginapp.data.local.datastore.UserPreferenceDataSourceImpl
import com.aeryz.seimbanginapp.data.local.datastore.appDataStore
import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSource
import com.aeryz.seimbanginapp.data.network.datasource.SeimbanginDataSourceImpl
import com.aeryz.seimbanginapp.data.network.service.ApiConfig
import com.aeryz.seimbanginapp.data.repository.AuthRepository
import com.aeryz.seimbanginapp.data.repository.AuthRepositoryImpl
import com.aeryz.seimbanginapp.ui.home.HomeViewModel
import com.aeryz.seimbanginapp.ui.login.LoginViewModel
import com.aeryz.seimbanginapp.ui.register.RegisterViewModel
import com.aeryz.seimbanginapp.ui.splash.SplashViewModel
import com.aeryz.seimbanginapp.utils.PreferenceDataStoreHelper
import com.aeryz.seimbanginapp.utils.PreferenceDataStoreHelperImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    fun getModules(): List<Module> = listOf(
        viewModelsModule,
        networkModule,
        dataSourceModule,
        repositoryModule
    )

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { ApiConfig.getApiService(get()) }
    }

    private val dataSourceModule = module {
        single { androidContext().appDataStore }
        single<SeimbanginDataSource> { SeimbanginDataSourceImpl(get()) }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<AuthRepository> { AuthRepositoryImpl(get()) }
    }

    private val viewModelsModule = module {
        viewModel { LoginViewModel(get(), get()) }
        viewModel { RegisterViewModel(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { HomeViewModel(get()) }
    }
}
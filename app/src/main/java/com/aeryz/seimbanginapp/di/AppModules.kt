package com.aeryz.seimbanginapp.di

import com.aeryz.seimbanginapp.data.local.datastore.appDataStore
import com.aeryz.seimbanginapp.utils.PreferenceDataStoreHelper
import com.aeryz.seimbanginapp.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
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
        //single { SinowApiService.invoke(get(), get()) }
    }

    private val dataSourceModule = module {
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val repositoryModule = module {
        //single<CourseRepository> { CourseRepositoryImpl(get()) }
    }

    private val viewModelsModule = module {
        //viewModelOf(::SplashViewModel)
        //viewModel { MainViewModel(get()) }
    }
}
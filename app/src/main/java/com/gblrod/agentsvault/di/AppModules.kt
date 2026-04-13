package com.gblrod.agentsvault.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gblrod.agentsvault.language.viewmodel.LanguageViewModel
import com.gblrod.agentsvault.local.PrefsDataStore
import com.gblrod.agentsvault.network.API
import com.gblrod.agentsvault.presentation.agents.viewmodel.AgentsViewModel
import com.gblrod.agentsvault.presentation.main.dataStore
import com.gblrod.agentsvault.presentation.maps.viewmodel.MapsViewModel
import com.gblrod.agentsvault.presentation.retry.viewmodel.RetryViewModel
import com.gblrod.agentsvault.presentation.theme.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // DataStore
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    singleOf(constructor = ::PrefsDataStore)

    // Network (Instance)
    single {
        Retrofit.Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Network (API)
    single<API> {
        get<Retrofit>().create(API::class.java)
    }

    // ViewModels
    viewModelOf(constructor = ::ThemeViewModel)
    viewModelOf(constructor = ::MapsViewModel)
    viewModelOf(constructor = ::RetryViewModel)
    viewModelOf(constructor = ::LanguageViewModel)
    viewModel {
        AgentsViewModel(
            prefsDataStore = get(),
            api = get())
    }
}
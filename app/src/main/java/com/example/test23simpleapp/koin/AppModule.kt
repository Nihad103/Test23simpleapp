package com.example.test23simpleapp.koin

import com.example.test23simpleapp.service.ApiService
import com.example.test23simpleapp.viewModel.MyViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
            Retrofit.Builder()
                .baseUrl("https://api.kanye.rest")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
    viewModel { MyViewModel(get()) }
}

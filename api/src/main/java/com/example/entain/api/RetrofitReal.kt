package com.example.entain.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object RetrofitReal {

    fun provideEndpoints(): Endpoints {
        return provideRetrofitInstance()
            .create(Endpoints::class.java)
    }

    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(getKotlinSerializationConverterFactory())
            .client(getClient())
            .build()
    }

    private fun getBaseUrl(): String {
        return "https://api.neds.com.au/"
    }

    fun getKotlinSerializationConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
    }

    fun getClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY // Logs all - headers request and response
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}

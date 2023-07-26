package com.example.entain.api

import org.koin.dsl.module

/**
 * Provides repo module for Koin to be fetched from app
 */
val apiModule = module {
    single { provideEndpoints() }
    single { ApiService(get()) }
    single { RaceRepo(get()) }
}

fun provideEndpoints(): Endpoints {
        return RetrofitReal.provideEndpoints()
}

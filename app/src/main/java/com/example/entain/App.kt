package com.example.entain

import androidx.multidex.MultiDexApplication
import com.example.entain.api.apiModule
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kartik.grevocab.base.StartKoinInterface
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : MultiDexApplication(), StartKoinInterface {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin()
    }

    override fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(apiModule, myModule)
        }
    }
}

val myModule = module {
    viewModel { RacesViewModel(get()) }
}

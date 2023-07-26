package com.example.entain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entain.api.RaceRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RacesViewModel(private val raceRepo: RaceRepo) : ViewModel(), KoinComponent {

    fun doStuff(){
        Log.e("vm","doStuff")
        viewModelScope.launch {
            raceRepo.getSortedRaces()
        }
    }
}

package com.example.entain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entain.api.APIResponse
import com.example.entain.api.RaceRepo
import com.example.entain.api.pojos.RaceDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RacesViewModel(private val raceRepo: RaceRepo) : ViewModel(), KoinComponent {

    val racesFlow = MutableStateFlow<List<RaceDetailsDisplay>>(arrayListOf())
    fun doStuff(){
        viewModelScope.launch {
            val response = raceRepo.getSortedRaces()
            when(response){
                is APIResponse.Success->{
                    val raceList = response.data as ArrayList<RaceDetails>
                    racesFlow.value = raceList.map { it.toDisplay() }
                }
                is APIResponse.Error ->{

                }
            }
        }
    }
}

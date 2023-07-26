package com.example.entain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entain.api.APIResponse
import com.example.entain.api.RaceRepo
import com.example.entain.api.pojos.RaceDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RacesViewModel(private val raceRepo: RaceRepo) : ViewModel(), KoinComponent {

    val racesFlow = MutableStateFlow<List<RaceDetailsDisplay>>(arrayListOf())
    var filter = MutableStateFlow(RaceCategory.NONE)
    fun fetchLatestData() {
        viewModelScope.launch {
            racesFlow.value = arrayListOf()
            delay(200)
            when (val response = raceRepo.getSortedRaces()) {
                is APIResponse.Success -> {
                    val raceList = response.data as ArrayList<RaceDetails>
                    val allRaces = raceList.map { it.toDisplay() }.filter { !it.hasPassed }
                    var filteredList = allRaces
                    if (filter.value != RaceCategory.NONE) {
                        filteredList = allRaces.filter { it.raceCategory == filter.value }
                    }
                    racesFlow.value = filteredList.take(5)
                }

                is APIResponse.Error -> {

                }
            }
        }
    }

    fun updateFilter(raceCategory: RaceCategory) {
        filter.value = raceCategory
        fetchLatestData()
    }
}

package com.example.entain.api

import com.example.entain.api.pojos.RaceDetails
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class RaceRepo(private val apiService: ApiService) {

    suspend fun getSortedRaces():APIResponse<ArrayList<RaceDetails>>{
        val races = apiService.fetchRaces()
        return if(races.isSuccessful){
            val raceDetailsList = arrayListOf<RaceDetails>()
            val race = races.body()!!
            val jsonElement = race.data.raceSummaries
            for(item in race.data.nextToGoIds){
                val raceDetails = Json.decodeFromJsonElement<RaceDetails>(jsonElement.jsonObject.get(item)!!)
                raceDetailsList.add(raceDetails)
            }
            raceDetailsList.sortBy { it.advertisedStart.seconds }
            APIResponse.Success(raceDetailsList)
        }else{
            APIResponse.Error(null,"Some error occurred")
        }
    }
}
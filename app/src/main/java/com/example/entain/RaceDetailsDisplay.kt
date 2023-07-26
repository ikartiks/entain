package com.example.entain

import com.example.entain.api.pojos.RaceDetails
import java.util.Date

data class RaceDetailsDisplay(val raceDetails: RaceDetails,
                              val startTime:String,
                              val hasPassed:Boolean = false,
val raceCategory: RaceCategory)

fun RaceDetails.toDisplay():RaceDetailsDisplay {

    val now = Date()
    var timeInMillis = this.advertisedStart.seconds.toLong()*1000L
    val raceDate = Date(timeInMillis)
    val hasPassed = now.before(raceDate)
    return RaceDetailsDisplay(this,
        "${this.advertisedStart.seconds}",
        hasPassed,
        this.categoryId.getFromCategory())

}

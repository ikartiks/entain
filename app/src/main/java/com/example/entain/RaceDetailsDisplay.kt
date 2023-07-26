package com.example.entain

import com.example.entain.api.pojos.RaceDetails
import java.text.SimpleDateFormat
import java.util.Date

data class RaceDetailsDisplay(val raceDetails: RaceDetails,
                              val startTime:String,
                              val hasPassed:Boolean = false,
                              val raceCategory: RaceCategory)

fun RaceDetails.toDisplay():RaceDetailsDisplay {

    val now = Date()
    var timeInMillis = this.advertisedStart.seconds.toLong()*1000L
    val raceDate = Date(timeInMillis)
    val simpleDateFormat = SimpleDateFormat("dd/MM/yy hh:mm:ss")
    val hasPassed = raceDate.before(now)
    return RaceDetailsDisplay(this,
        "${simpleDateFormat.format(raceDate)}",
        hasPassed,
        this.categoryId.getFromCategory())

}

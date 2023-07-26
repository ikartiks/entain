package com.example.entain.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDetails(

    @SerialName("race_id") var raceId: String,
    @SerialName("race_name") var raceName: String,
    @SerialName("race_number") var raceNumber: Int,
    @SerialName("meeting_id") var meetingId: String,
    @SerialName("meeting_name") var meetingName: String,
    @SerialName("category_id") var categoryId: String,
    @SerialName("advertised_start") var advertisedStart: AdvertisedStart,
    @SerialName("race_form") var raceForm: RaceForm? = RaceForm(),
    @SerialName("venue_id") var venueId: String,
    @SerialName("venue_name") var venueName: String,
    @SerialName("venue_state") var venueState: String,
    @SerialName("venue_country") var venueCountry: String

)
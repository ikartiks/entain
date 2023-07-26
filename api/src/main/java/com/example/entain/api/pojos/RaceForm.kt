package com.example.entain.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RaceForm(

    @SerialName("distance") var distance: Int? = null,
    @SerialName("distance_type") var distanceType: DistanceType? = DistanceType(),
    @SerialName("distance_type_id") var distanceTypeId: String? = null,
    @SerialName("track_condition") var trackCondition: TrackCondition? = TrackCondition(),
    @SerialName("track_condition_id") var trackConditionId: String? = null,
    @SerialName("weather") var weather: Weather? = Weather(),
    @SerialName("weather_id") var weatherId: String? = null,
    @SerialName("race_comment") var raceComment: String? = null,
    @SerialName("additional_data") var additionalData: String? = null,
    @SerialName("generated") var generated: Int? = null,
    @SerialName("silk_base_url") var silkBaseUrl: String? = null,
    @SerialName("race_comment_alternative") var raceCommentAlternative: String? = null

)
package com.example.entain.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackCondition(

    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("short_name") var shortName: String? = null

)
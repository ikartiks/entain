package com.example.entain.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


@Serializable
data class Data(
    @SerialName("next_to_go_ids") var nextToGoIds: ArrayList<String> = arrayListOf(),
    @SerialName("race_summaries") var raceSummaries: JsonElement
)
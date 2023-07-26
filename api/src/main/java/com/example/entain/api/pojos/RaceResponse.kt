package com.example.entain.api.pojos

import kotlinx.serialization.Serializable


@Serializable
data class RaceResponse(
    var status: Int,
    var data: Data,
    var message: String
)
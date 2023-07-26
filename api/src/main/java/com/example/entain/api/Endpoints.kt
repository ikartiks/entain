package com.example.entain.api

import com.example.entain.api.pojos.RaceResponse
import retrofit2.Response
import retrofit2.http.GET

interface Endpoints {

    @GET("/rest/v1/racing/?method=nextraces&count=10")
    suspend fun getRaces(): Response<RaceResponse>
}

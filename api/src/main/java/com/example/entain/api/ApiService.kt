package com.example.entain.api

class ApiService(private var endpoints: Endpoints) {

    suspend fun fetchRaces() = endpoints.getRaces()
}

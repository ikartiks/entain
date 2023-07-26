package com.example.entain.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import retrofit2.Retrofit

class APITest: KoinTest {

    private val mockWebServer = MockWebServer()

    @BeforeEach
    fun setup() {
        mockWebServer.start(8080)
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }
    @Test
    fun testSuccessResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FileReader.readStringFromFile("success.json"))
        )

        runBlocking {
            val apiService = ApiService(getEndpointsForTests())
            val response =apiService.fetchRaces()
            val raceResponse = response.body()
            val size = raceResponse!!.data.nextToGoIds.size
            Assertions.assertEquals(size, 10)
        }
    }

    @Test
    fun testFailureResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(FileReader.readStringFromFile("fail.json"))
        )
        val apiService = ApiService(getEndpointsForTests())
        runBlocking {
            val response =apiService.fetchRaces()
            Assertions.assertEquals(response.code(), 404)
        }
    }

    private fun getEndpointsForTests(): Endpoints {

        return Retrofit.Builder()
            .addConverterFactory(RetrofitReal.getKotlinSerializationConverterFactory())
            .baseUrl("http://127.0.0.1:8080/")
            .client(RetrofitReal.getClient())
            .build().create(Endpoints::class.java)
    }
}
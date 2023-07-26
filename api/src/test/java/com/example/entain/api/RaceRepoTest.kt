package com.example.entain.api

import com.example.entain.api.pojos.AdvertisedStart
import com.example.entain.api.pojos.Data
import com.example.entain.api.pojos.RaceDetails
import com.example.entain.api.pojos.RaceResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RaceRepoTest : BaseTest() {

    private val apiService: ApiService = mockk()

    private lateinit var raceRepo: RaceRepo

    @BeforeEach
    fun setUp() {
        raceRepo = RaceRepo(apiService)

        val raceDetails = RaceDetails("1", "Fake race name", 1,
            "asdads","Meeting name","1", AdvertisedStart(50000), null,
            "Au","Australia","QLD","AU")

        val raceElement = Json.encodeToJsonElement(raceDetails)
        val mutableMap = mutableMapOf<String,JsonElement>()
        mutableMap["1"] = raceElement
        mutableMap["2"] = raceElement
        val element = JsonObject(mutableMap)
        coEvery { apiService.fetchRaces() } returns Response.success(
            RaceResponse(200,
                Data(arrayListOf("1","2"), element),
                "this is good"
            ))
    }

    @Test
    fun testSuccessfulFetch() {
        runBlocking {
            val response = raceRepo.getSortedRaces()
            assertEquals(response.data!![0].raceId, "1")
            assertEquals(response.data!![0].meetingName, "Meeting name")
        }
    }

    @Test
    fun testUnSuccessfulFetch() {
        coEvery { apiService.fetchRaces() } returns Response.error(400, ResponseBody.create(null,"err"))
        runBlocking {
            val response = raceRepo.getSortedRaces()
            assertNull(response.data)
            assertEquals(response.message, "Some error occurred")
        }
    }
}

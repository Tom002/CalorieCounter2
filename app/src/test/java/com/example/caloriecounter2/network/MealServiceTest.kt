package com.example.caloriecounter2.network

import com.example.caloriecounter2.model.network.AddOrEditMealDto
import com.example.caloriecounter2.model.network.MealDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class MealServiceTest {

    private val mockWebServer = MockWebServer()

    private val getByIdResponse: String = """
        {
            "id": 1,
            "name": "Scrambled eggs",
            "calories": 500,
            "proteinInGrams": 40,
            "carbInGrams": 50,
            "fatInGrams": 10,
            "date": "2022-05-18"
        }
    """.trimIndent()

    private val getByIdNotFoundResponse: String = """
        {
          "type": "https://tools.ietf.org/html/rfc7231#section-6.5.4",
          "title": "Not Found",
          "status": 404,
          "traceId": "00-b31bb983fe92fb30a4b06606f43edb51-5bc11f5590dd7c8c-00"
        }
    """.trimIndent()

    private val expectedAddOrEditBody: String = """
        {
            "name": "Scrambled eggs",
            "calories": 500,
            "proteinInGrams": 40,
            "carbInGrams": 50,
            "fatInGrams": 10,
            "date": "2022-05-18"
        }
    """.trimIndent()

    private val addOrEditMealDto: AddOrEditMealDto = AddOrEditMealDto(
        "Scrambled eggs",
        500,
        40.0,
        50.0,
        10.0,
        DateTime(2022, 5, 18, 0, 0).toDate()
    )

    private val listByDateResponse: String = """
        [
          {
            "id": 1,
            "name": "Scrambled eggs",
            "calories": 500,
            "proteinInGrams": 40,
            "carbInGrams": 50,
            "fatInGrams": 10,
            "date": "2022-05-18"
          },
          {
            "id": 2,
            "name": "Chciken with rice",
            "calories": 500,
            "proteinInGrams": 40,
            "carbInGrams": 50,
            "fatInGrams": 10,
            "date": "2022-05-18"
          },
          {
            "id": 3,
            "name": "Greek salad",
            "calories": 500,
            "proteinInGrams": 40,
            "carbInGrams": 50,
            "fatInGrams": 10,
            "date": "2022-05-18"
          }
        ]
    """.trimIndent()

    fun createService(): MealService {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MealService::class.java)
    }

    @Before
    fun setUp() {
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `it should GET item by Id`() {

        val mealId: Long = 1
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getByIdResponse)
            )

            val getResponse = createService().getMealById(mealId);
            val recordedRequest = mockWebServer.takeRequest()

            // Assert for sending the request correctly
            assert(recordedRequest.path.equals("/api/meal/1"))
            assert(recordedRequest.method.equals("GET"))

            // Assert for parsing/handling the response correctly
            var gson = Gson()
            var mealDto = gson?.fromJson(getByIdResponse, MealDto::class.java)
            assertEquals(getResponse.calories, mealDto.calories)
            assertEquals(getResponse.carbInGrams, mealDto.carbInGrams, 0.0001)
            assertEquals(getResponse.fatInGrams, mealDto.fatInGrams, 0.0001)
            assertEquals(getResponse.proteinInGrams, mealDto.proteinInGrams, 0.0001)
            assertEquals(getResponse.date, mealDto.date)
            assertEquals(getResponse.name, mealDto.name)
        }
    }

    @Test
    fun `it should throw exception for 404 response`() {

        val mealId: Long = 10
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                    .setBody(getByIdNotFoundResponse))

            val exception = try {
                createService().getMealById(mealId)
                null
            } catch (exception: Exception){
                exception
            }

            val httpException = exception as? HttpException
            assert(httpException != null)
            assert(httpException!!.code() == 404)
            assert(httpException!!.message() == "Client Error")
        }
    }

    @Test
    fun `it should GET list by date`() {

        val mealId: Long = 1
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(listByDateResponse)
            )

            val getResponse = createService().fetchMealsForDate(2022, 5, 18);
            val recordedRequest = mockWebServer.takeRequest()

            // Assert for sending the request correctly
            assert(recordedRequest.path.equals("/api/meal?year=2022&month=5&day=18"))
            assert(recordedRequest.method.equals("GET"))

            // Assert for parsing/handling the response correctly
            var gson = Gson()
            val itemType = object : TypeToken<List<MealDto>>() {}.type
            var mealDtoList = gson.fromJson<List<MealDto>>(listByDateResponse, itemType)
            assertEquals(mealDtoList.size, 3)
        }
    }

    @Test
    fun `it should send DELETE correctly`() {

        val mealId: Long = 1
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody("")
            )

            val deleteResponse = createService().deleteMeal(mealId);
            val recordedRequest = mockWebServer.takeRequest()

            // Assert for sending the request correctly
            assert(recordedRequest.path.equals("/api/meal/1"))
            assert(recordedRequest.method.equals("DELETE"))
        }
    }

    @Test
    fun `it should send PUT correctly`() {
        val mealId: Long = 1
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody("")
            )

            val putResponse = createService().updateMeal(mealId, addOrEditMealDto);
            val recordedRequest = mockWebServer.takeRequest()

            // Assert for sending the request correctly
            assert(recordedRequest.path.equals("/api/meal/1"))
            assert(recordedRequest.method.equals("PUT"))
            val parser = JsonParser()
            val json1: JsonElement = parser.parse(expectedAddOrEditBody)
            val json2: JsonElement = parser.parse(recordedRequest.body.readUtf8())
            assertEquals(json1, json2);
        }
    }

    @Test
    fun `it should send POST correctly`() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getByIdResponse)
            )

            val addResponse = createService().addMeal(addOrEditMealDto);
            val recordedRequest = mockWebServer.takeRequest()

            // Assert for sending the request correctly
            assert(recordedRequest.path.equals("/api/meal"))
            assert(recordedRequest.method.equals("POST"))
            val parser = JsonParser()
            val json1: JsonElement = parser.parse(expectedAddOrEditBody)
            val json2: JsonElement = parser.parse(recordedRequest.body.readUtf8())
            assertEquals(json1, json2);
        }
    }
}
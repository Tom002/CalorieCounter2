package com.example.caloriecounter2.network

import com.example.caloriecounter2.model.network.AddMealDto
import com.example.caloriecounter2.model.network.MealDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.*

interface MealService {
    @GET("api/meals")
    suspend fun fetchMealsForDate(date: Date): Call<List<MealDto>>

    @POST("api/meals")
    suspend fun addMeal(meal: AddMealDto): Call<ResponseBody>

    @PUT("api/meals/{mealId}")
    suspend fun updateMeal(meal: MealDto): Call<ResponseBody>

    @DELETE("api/meals/{mealId}")
    suspend fun deleteMeal(mealId: Long): Call<ResponseBody>
}
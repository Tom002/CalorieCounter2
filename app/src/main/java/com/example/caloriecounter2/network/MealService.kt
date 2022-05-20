package com.example.caloriecounter2.network

import com.example.caloriecounter2.model.network.AddOrEditMealDto
import com.example.caloriecounter2.model.network.MealDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MealService {
    @GET("api/meal")
    suspend fun fetchMealsForDate(@Query("year") year: Int?, @Query("month") month: Int?, @Query("day") day: Int?): List<MealDto>

    @GET("api/meal/{mealId}")
    suspend fun getMealById(@Path("mealId") mealId:Long): MealDto

    @POST("api/meal")
    suspend fun addMeal(@Body addMealDto: AddOrEditMealDto): MealDto

    @PUT("api/meal/{mealId}")
    suspend fun updateMeal(@Path("mealId") mealId:Long, @Body editMealDto: AddOrEditMealDto): ResponseBody

    @DELETE("api/meal/{mealId}")
    suspend fun deleteMeal(@Path("mealId") mealId: Long): ResponseBody
}
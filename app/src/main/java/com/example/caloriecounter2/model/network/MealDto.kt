package com.example.caloriecounter2.model.network

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MealDto(
    val id: Long,
    val name: String,
    val calories: Int,
    val proteinInGrams: Double,
    val carbInGrams: Double,
    val fatInGrams: Double,
    var date: Date,
)

@JsonClass(generateAdapter = true)
data class AddMealDto(
    val name: String,
    val calories: Int,
    val proteinInGrams: Double,
    val carbInGrams: Double,
    val fatInGrams: Double,
    var date: Date,
)
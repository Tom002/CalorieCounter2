package com.example.caloriecounter2.model.network

import com.squareup.moshi.JsonClass
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.util.*

@JsonClass(generateAdapter = true)
data class MealDto (
    val id: kotlin.Long,
    val name: kotlin.String,
    val calories: kotlin.Int,
    val proteinInGrams: kotlin.Double,
    val carbInGrams: kotlin.Double,
    val fatInGrams: kotlin.Double,
    val date: Date
) {
}

@JsonClass(generateAdapter = true)
data class AddOrEditMealDto (
    val name: kotlin.String,
    val calories: kotlin.Int,
    val proteinInGrams: kotlin.Double,
    val carbInGrams: kotlin.Double,
    val fatInGrams: kotlin.Double,
    val date: Date
) {
}
package com.example.caloriecounter2.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Meal   (
    @PrimaryKey val id: Long,
    val name: String,
    val calories: Int,
    val proteinInGrams: Double,
    val carbInGrams: Double,
    val fatInGrams: Double,
    val year: Int,
    val month: Int,
    val day: Int,
)

fun Meal.macroString(): String {
    return "${this.proteinInGrams}P/${this.carbInGrams}CH/${this.fatInGrams}F"
}


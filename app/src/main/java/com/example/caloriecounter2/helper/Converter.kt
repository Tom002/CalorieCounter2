package com.example.caloriecounter2.helper

import androidx.databinding.InverseMethod
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*


object Converter {

    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(
        value: Int
    ): String {
        return value.toString()
    }

    @JvmStatic fun stringToInt(
        value: String
    ): Int {
        return value.toInt()
    }

    @InverseMethod("stringToDouble")
    @JvmStatic fun doubleToString(
        value: Double
    ): String {
        return value.toString()
    }

    @JvmStatic fun stringToDouble(
        value: String
    ): Double {
        return value.toDouble()
    }

    @InverseMethod("stringToDate")
    @JvmStatic fun dateToString(
        value: Date
    ): String {
        return DateTime(value).toString("yyyy:MM:dd")
    }

    @JvmStatic fun stringToDate(
        value: String
    ): Date {
        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("yyyy:MM:dd")
        return fmt.parseDateTime(value).toDate()
    }
}
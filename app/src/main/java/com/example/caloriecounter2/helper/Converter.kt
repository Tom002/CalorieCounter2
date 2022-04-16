package com.example.caloriecounter2.helper

import android.widget.EditText
import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("stringToDate")
    @JvmStatic fun dateToString(
        view: EditText, oldValue: Long,
        value: Long
    ): String {
        // Converts long to String.
    }

    @JvmStatic fun stringToDate(
        view: EditText, oldValue: String,
        value: String
    ): Long {
        // Converts String to long.
    }
}
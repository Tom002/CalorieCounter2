package com.example.caloriecounter2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.caloriecounter2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MealsActivity : AppCompatActivity() {

    lateinit var mealsAdapter: MealsAdapter
    lateinit var mealsViewModel: MealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mealsViewModel = ViewModelProvider(this).get(MealsViewModel::class.java)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mealsAdapter = MealsAdapter(this, mealsViewModel)
        listMeals.adapter = mealsAdapter

        mealsViewModel.mealList.observe(this, {
                meals -> mealsAdapter.submitList(meals)
        })
    }
}
package com.example.caloriecounter2.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriecounter2.R
import com.example.caloriecounter2.databinding.MealRowBinding
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.ui.addMeal.AddMealActivity
import com.example.caloriecounter2.ui.editMeal.EditMealActivity

public class MealsAdapter (private val context: Context,
                           private val mealsViewModel: MealsViewModel) : ListAdapter<Meal, MealsAdapter.ViewHolder>(MealDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: MealRowBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.meal_row,
            parent, false)
        binding.adapter = this
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    fun deleteMeal(meal: Meal) {
        mealsViewModel.delete(meal)
    }

    fun editMeal(meal: Meal) {
        val intent = Intent(context, EditMealActivity::class.java)
        intent.putExtra(EditMealActivity.KEY_ID, meal.id)
        context.startActivity(intent)
    }

    class ViewHolder(val binding: MealRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.meal = meal
        }
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

}
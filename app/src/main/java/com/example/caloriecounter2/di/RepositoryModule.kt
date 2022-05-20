package com.example.caloriecounter2.di

import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import com.example.caloriecounter2.ui.main.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        mealService: MealService,
        mealDao: MealDao
    ): MealsRepository {
        return MealsRepository(mealService, mealDao)
    }
}
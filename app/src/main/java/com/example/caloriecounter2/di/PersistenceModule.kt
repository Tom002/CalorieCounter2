package com.example.caloriecounter2.di

import android.app.Application
import androidx.room.Room
import com.example.caloriecounter2.persistence.AppDatabase
import com.example.caloriecounter2.persistence.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "CalorieCounter.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(appDatabase: AppDatabase): MealDao {
        return appDatabase.mealDao()
    }
}
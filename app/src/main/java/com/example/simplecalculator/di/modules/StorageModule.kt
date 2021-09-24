package com.example.simplecalculator.di.modules

import android.content.Context
import androidx.room.Room
import com.example.simplecalculator.data.database.CalculatorDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(context: Context) : CalculatorDataBase {
            return Room.databaseBuilder(
                context,
                CalculatorDataBase::class.java, "database.db"
            ).build()
        }
    }
}
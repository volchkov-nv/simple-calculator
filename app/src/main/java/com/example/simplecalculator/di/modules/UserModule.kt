package com.example.simplecalculator.di.modules

import android.content.Context
import com.example.simplecalculator.data.database.CalculatorDataBase
import com.example.simplecalculator.data.prefs.SharedPreferenceHolder
import com.example.simplecalculator.data.repos.SimpleCalculatorRepositoryImpl
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserModule {

    companion object {

        @Provides
        @Singleton
        fun provideSimpleCalculatorRepository(
            context: Context,
            database: CalculatorDataBase,
            preferences: SharedPreferenceHolder
        ) : SimpleCalculatorRepository {
            return SimpleCalculatorRepositoryImpl(context, database, preferences)
        }

    }
}
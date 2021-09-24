package com.example.simplecalculator.di.modules

import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.calculator.SimpleCalculatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SimpleCalculatorModule {

    companion object {
        @Provides
        @Singleton
        fun provideSimpleCalculator(): SimpleCalculator {
            return SimpleCalculatorImpl()
        }
    }
}
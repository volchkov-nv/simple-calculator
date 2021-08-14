package com.example.simplecalculator.di.components

import android.content.Context
import com.example.simplecalculator.di.modules.NavigationModule
import com.example.simplecalculator.di.modules.SimpleCalculatorModule
import com.example.simplecalculator.presentation.activities.MainViewModel
import com.example.simplecalculator.presentation.screens.SimpleCalculatorViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NavigationModule::class,
        SimpleCalculatorModule::class
    ]
)
interface ApplicationComponent {

    fun simpleCalculatorViewModel() : SimpleCalculatorViewModel
    fun mainViewModel() : MainViewModel

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}
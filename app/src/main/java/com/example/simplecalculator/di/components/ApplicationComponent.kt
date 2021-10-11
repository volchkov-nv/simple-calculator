package com.example.simplecalculator.di.components

import android.content.Context
import com.example.simplecalculator.di.modules.NavigationModule
import com.example.simplecalculator.di.modules.SimpleCalculatorModule
import com.example.simplecalculator.di.modules.StorageModule
import com.example.simplecalculator.di.modules.UserModule
import com.example.simplecalculator.presentation.activities.MainViewModel
import com.example.simplecalculator.presentation.screens.calculator.SimpleCalculatorViewModel
import com.example.simplecalculator.presentation.screens.history.HistoryViewModel
import com.example.simplecalculator.presentation.screens.settings.SettingsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        UserModule::class,
        NavigationModule::class,
        SimpleCalculatorModule::class,
        StorageModule::class
    ]
)
interface ApplicationComponent {

    fun simpleCalculatorViewModel() : SimpleCalculatorViewModel
    fun historyViewModel() : HistoryViewModel
    fun settingsViewModel() : SettingsViewModel
    fun mainViewModel() : MainViewModel

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}
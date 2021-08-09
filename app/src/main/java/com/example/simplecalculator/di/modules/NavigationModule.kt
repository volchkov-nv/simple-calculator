package com.example.simplecalculator.di.modules

import com.example.simplecalculator.app.navigation.Navigator
import com.example.simplecalculator.app.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class NavigationModule {

    companion object {
        @Provides
        @Singleton
        fun provideNavigation(): Navigator {
            return NavigatorImpl()
        }
    }
}
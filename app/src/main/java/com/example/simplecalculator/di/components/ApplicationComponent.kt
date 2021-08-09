package com.example.simplecalculator.di.components

import android.content.Context
import com.example.simplecalculator.di.modules.NavigationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NavigationModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}
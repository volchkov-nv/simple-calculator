package com.example.simplecalculator.di

import android.content.Context
import com.example.simplecalculator.di.components.ApplicationComponent
import com.example.simplecalculator.di.components.DaggerApplicationComponent

object DI {

    lateinit var app: ApplicationComponent
    private lateinit var appContext: Context

    fun init(context: Context) {
        this.app = DaggerApplicationComponent
            .builder()
            .context(context)
            .build()
        appContext = context
    }
}
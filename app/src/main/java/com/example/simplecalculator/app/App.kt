package com.example.simplecalculator.app

import android.app.Application
import com.example.simplecalculator.di.DI
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
        Timber.plant(Timber.DebugTree())
    }
}
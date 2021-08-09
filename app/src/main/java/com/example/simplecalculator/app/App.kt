package com.example.simplecalculator.app

import android.app.Application
import com.example.simplecalculator.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
    }
}
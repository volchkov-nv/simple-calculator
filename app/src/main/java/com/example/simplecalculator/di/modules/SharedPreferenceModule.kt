package com.example.simplecalculator.di.modules

import android.content.Context
import com.example.simplecalculator.data.prefs.SharedPreferenceHolder
import com.example.simplecalculator.data.prefs.SharedPreferenceHolderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    companion object {

        @Provides
        @Singleton
        fun provideSharedPreference(context: Context) : SharedPreferenceHolder {
            return SharedPreferenceHolderImpl(context)
        }
    }
}
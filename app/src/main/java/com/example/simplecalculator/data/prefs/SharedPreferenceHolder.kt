package com.example.simplecalculator.data.prefs

interface SharedPreferenceHolder {

    fun getMemory() : String?

    fun saveMemory(value: String)

    fun isMemoryEmpty() : Boolean

}
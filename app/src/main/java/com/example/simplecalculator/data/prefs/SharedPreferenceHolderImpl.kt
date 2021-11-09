package com.example.simplecalculator.data.prefs

import android.content.Context

class SharedPreferenceHolderImpl(private val context: Context) : SharedPreferenceHolder {

    companion object {
        const val MEMORY_VALUE = "MEMORY_VALUE"
    }

    private val sharedPreference =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override fun getMemory() : String? {
        return getString(MEMORY_VALUE)
    }

    override fun saveMemory(value: String) {
        putString(MEMORY_VALUE, value)
    }

    override fun isMemoryEmpty(): Boolean {
        val data = getString(MEMORY_VALUE)
        return data == null || data.isEmpty()
    }

    private fun getString(key: String) = sharedPreference.getString(key, "")
    private fun putString(key: String, string: String) {
        sharedPreference.edit().putString(key, string).apply()
    }





}
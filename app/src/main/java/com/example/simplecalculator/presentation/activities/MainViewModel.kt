package com.example.simplecalculator.presentation.activities

import androidx.fragment.app.FragmentManager
import com.example.simplecalculator.app.navigation.Navigator
import com.example.simplecalculator.presentation.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel() {

    fun goToMainScreen() {
        navigator.goToMainScreen()
    }

    fun afterInit(supportFragmentManager: FragmentManager) {
        navigator.initFragmentManager(supportFragmentManager)
    }

    fun goToSettings() {
        navigator.goToSettings()
    }

    fun goToHistory() {
        navigator.goToHistory()
    }

    fun goToCalculator() {
        navigator.goToMainScreen()
    }

}
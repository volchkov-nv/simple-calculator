package com.example.simplecalculator.app.navigation

import androidx.fragment.app.FragmentManager

interface Navigator {

    fun initFragmentManager(supportFragmentManager: FragmentManager)

    fun onBackFragment()

    fun goToMainScreen()

    fun goToSettings()
}
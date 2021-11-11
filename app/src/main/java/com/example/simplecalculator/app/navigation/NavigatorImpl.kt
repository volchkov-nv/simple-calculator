package com.example.simplecalculator.app.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.simplecalculator.R
import com.example.simplecalculator.presentation.screens.calculator.SimpleCalculatorFragment
import com.example.simplecalculator.presentation.screens.history.HistoryFragment
import com.example.simplecalculator.presentation.screens.settings.SettingsFragment

class NavigatorImpl : Navigator {

    private lateinit var supportFragmentManager: FragmentManager

    override fun initFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun onBackFragment() {

    }

    override fun goToMainScreen() {
        replaceFragment(SimpleCalculatorFragment())
    }

    override fun goToSettings() {
        replaceFragment(SettingsFragment())
    }

    override fun goToHistory() {
        replaceFragment(HistoryFragment())
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentFrame, fragment)
            addToBackStack(fragment.javaClass.name)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentFrame, fragment)
        }
    }
}
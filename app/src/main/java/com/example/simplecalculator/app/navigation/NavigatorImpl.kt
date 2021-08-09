package com.example.simplecalculator.app.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.simplecalculator.R

class NavigatorImpl : Navigator {

    private lateinit var supportFragmentManager: FragmentManager

    override fun onBackFragment() {

    }

    override fun goToMainScreen() {

    }

    override fun goToSettings() {

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
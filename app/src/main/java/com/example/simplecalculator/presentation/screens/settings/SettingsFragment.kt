package com.example.simplecalculator.presentation.screens.settings

import android.os.Bundle
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment

class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.settings_fragment) {

    override val viewModel: SettingsViewModel by lazy {
        injectViewModel {
            DI.app.settingsViewModel()
        }
    }

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }
}
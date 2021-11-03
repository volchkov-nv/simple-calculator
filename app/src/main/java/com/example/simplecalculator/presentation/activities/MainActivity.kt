package com.example.simplecalculator.presentation.activities

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseActivity
import com.example.simplecalculator.presentation.widgets.menu_panel.MainNavigationPanel

class MainActivity : BaseActivity<MainViewModel>() {

    var mainNavigationPanel : MainNavigationPanel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainNavigationPanel = findViewById(R.id.mainPanel)
        setMainMenuClickAction()
    }


    override val viewModel: MainViewModel by lazy {
        injectViewModel {
            DI.app.mainViewModel()
        }
    }

    override fun initViewModel() {
        viewModel.afterInit(supportFragmentManager)
    }

    private fun setMainMenuClickAction() {

        mainNavigationPanel?.setHistoryListener {
            findNavController(R.id.fragmentFrame).navigate(R.id.historyFragment)
        }
        mainNavigationPanel?.setCalcListener {
            findNavController(R.id.fragmentFrame).navigate(R.id.simpleCalculatorFragment)
        }
        mainNavigationPanel?.setSettingsListener {
            findNavController(R.id.fragmentFrame).navigate(R.id.settingsFragment)
        }
    }

    override fun onBackPressed() {

    }
}
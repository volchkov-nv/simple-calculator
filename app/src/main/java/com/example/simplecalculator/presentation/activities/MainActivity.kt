package com.example.simplecalculator.presentation.activities

import android.os.Bundle
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.goToMainScreen()
    }

    override val viewModel: MainViewModel by lazy {
        injectViewModel {
            DI.app.mainViewModel()
        }
    }

    override fun initViewModel() {
        viewModel.afterInit(supportFragmentManager)
    }
}
package com.example.simplecalculator.presentation.screens

import android.os.Bundle
import android.widget.TextView
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment

class SimpleCalculatorFragment : BaseFragment<SimpleCalculatorViewModel>(R.layout.simple_calculator) {

    override val viewModel: SimpleCalculatorViewModel by lazy {
        injectViewModel {
            DI.app.simpleCalculatorViewModel()
        }
    }

    override fun initViewModel() {

    }

    override fun initUi(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
    }


}
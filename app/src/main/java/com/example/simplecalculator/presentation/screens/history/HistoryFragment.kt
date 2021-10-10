package com.example.simplecalculator.presentation.screens.history

import android.os.Bundle
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment

class HistoryFragment : BaseFragment<HistoryViewModel>(R.layout.history_fragment) {

    override val viewModel: HistoryViewModel by lazy {
        injectViewModel {
            DI.app.historyViewModel()
        }
    }

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }
}
package com.example.simplecalculator.presentation.screens.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.HistoryFragmentBinding
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment

class HistoryFragment : BaseFragment<HistoryViewModel>(R.layout.history_fragment) {

    override val viewModel: HistoryViewModel by lazy {
        injectViewModel {
            DI.app.historyViewModel()
        }
    }

    private lateinit var binding : HistoryFragmentBinding

    override fun initViewModel() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HistoryFragmentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }
}
package com.example.simplecalculator.presentation.screens

import android.os.Bundle
import android.view.View
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.SimpleCalculatorBinding
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment
import com.example.simplecalculator.presentation.widgets.CalculatorButton

class SimpleCalculatorFragment : BaseFragment<SimpleCalculatorViewModel>(R.layout.simple_calculator) {

    override val viewModel: SimpleCalculatorViewModel by lazy {
        injectViewModel {
            DI.app.simpleCalculatorViewModel()
        }
    }

    private lateinit var binding : SimpleCalculatorBinding
    private lateinit var numberList: List<CalculatorButton>
    private lateinit var operatorList: List<CalculatorButton>


    override fun initViewModel() {
        viewModel.screenValueUpdate.subscribe {
            binding.screen.updateValues(it.first, it.second)
        }
        viewModel.screenSizeUpdate.subscribe {
            viewModel.updateMaxSize(binding.screen.getViewSize())
        }
        viewModel.operatorLock.subscribe {  state ->
            operatorList.forEach {
                it.isEnabled = state
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SimpleCalculatorBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        numberList = getNumberList()
        operatorList = getOperatorList()

        numberList.forEach{ view ->
            view.setOnClickListener {
                viewModel.numberAction(view.getSymbolChar())
            }
        }
        operatorList.forEach { view ->
            view.setOnClickListener {
                viewModel.operatorAction(view.getSymbolChar())
            }
        }

        binding.cleanButton.setOnClickListener {
            viewModel.clear()
        }
        binding.resultButton.setOnClickListener {
            viewModel.result()
        }
        binding.backspaceButton.setOnClickListener {
            viewModel.backSpace()
        }
    }

    private fun getNumberList() : List<CalculatorButton> {
        return arrayListOf(
            binding.zeroButton,
            binding.oneButton,
            binding.twoButton,
            binding.threeButton,
            binding.fourButton,
            binding.fiveButton,
            binding.sixButton,
            binding.sevenButton,
            binding.eightButton,
            binding.nineButton,
            binding.pointButton,
        )
    }

    private fun getOperatorList() : List<CalculatorButton> {
        return arrayListOf(
            binding.percentButton,
            binding.divideButton,
            binding.multiplyButton,
            binding.minusButton,
            binding.plusButton,
        )
    }

    override fun onStart() {
        super.onStart()
    }


}
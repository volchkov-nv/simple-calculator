package com.example.simplecalculator.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.CalculatorScreenBinding
import com.example.simplecalculator.domain.models.OperationModel
import java.lang.StringBuilder

class CalculatorScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mainValue = ""
    private var resultValue = ""

    private lateinit var binding : CalculatorScreenBinding

    companion object {
        private val MAX_SIZE = 17
    }

    init {
        View.inflate(context, R.layout.calculator_screen, this)
        binding = CalculatorScreenBinding.inflate(LayoutInflater.from(context), this, true)

    }

    fun updateValues(textMain: String, textResult: String) {
        binding.mainInput.text = textMain
        binding.resultInput.text = textResult
    }

    fun updateScreen(operationModel: OperationModel) {
        val builder = StringBuilder()
        builder.append(operationModel.firstValue).append(" ")
            .append(operationModel.operator).append(" ")
            .append(operationModel.secondValue).append(" ")
        binding.mainInput.text = builder.toString()
        if (operationModel.error.isNotEmpty()) {
            binding.resultInput.text = operationModel.error
        } else {
            binding.resultInput.text = operationModel.result
        }
    }

    fun cleanView() {
        binding.mainInput.text = ""
        binding.resultInput.text = ""
    }

    fun getViewSize() : Int {
        return MAX_SIZE
    }
}
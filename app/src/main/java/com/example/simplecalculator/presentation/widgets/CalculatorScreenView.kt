package com.example.simplecalculator.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.CalculatorScreenBinding

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
        binding = CalculatorScreenBinding.inflate(LayoutInflater.from(context))

    }

    fun updateValues(textMain: String, textResult: String) {
        binding.mainInput.text = textMain
        binding.resultInput.text = textResult
    }

    fun cleanView() {
        binding.mainInput.text = ""
        binding.resultInput.text = ""
    }

    fun getViewSize() : Int {
        return MAX_SIZE
    }
}
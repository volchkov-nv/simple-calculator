package com.example.simplecalculator.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.CalculatorButtonBinding

class CalculatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CalculatorButtonBinding
    private var symbol : String = "?"

    init {
        View.inflate(context, R.layout.calculator_button, this)
        binding = CalculatorButtonBinding.inflate(LayoutInflater.from(context),this, true)

        obtainAttrs(attrs, R.styleable.CalculatorButton) {

            val buttonSymbol = it.getString(R.styleable.CalculatorButton_buttonSymbol)
            val backgroundColor = it.getColor(R.styleable.CalculatorButton_buttonBackground,
                ContextCompat.getColor(context, R.color.light_cream_white))
            val buttonTextColor = it.getColor(R.styleable.CalculatorButton_buttonTextColor,
                ContextCompat.getColor(context, R.color.gravel))

            buttonSymbol?.let {
                symbol = it
            }

            binding.buttonSymbol.text = buttonSymbol
            binding.buttonSymbol.setBackgroundColor(backgroundColor)
            binding.buttonSymbol.setTextColor(buttonTextColor)
        }
    }

    fun getSymbolChar() : Char {
        if (symbol.isNotEmpty()) return symbol[0]
        return '?'
    }

}
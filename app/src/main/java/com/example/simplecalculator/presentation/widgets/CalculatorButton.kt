package com.example.simplecalculator.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.simplecalculator.R

class CalculatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var button: TextView? = null

    init {
        View.inflate(context, R.layout.calculator_button, this)
        button = findViewById(R.id.button_symbol)

        obtainAttrs(attrs, R.styleable.CalculatorButton) {

            val buttonSymbol = it.getString(R.styleable.CalculatorButton_buttonSymbol)
            val backgroundColor = it.getColor(R.styleable.CalculatorButton_buttonBackground,
                ContextCompat.getColor(context, R.color.light_cream_white))
            val buttonTextColor = it.getColor(R.styleable.CalculatorButton_buttonTextColor,
                ContextCompat.getColor(context, R.color.gravel))

            button?.text = buttonSymbol
            button?.setBackgroundColor(backgroundColor)
            button?.setTextColor(buttonTextColor)
        }
    }
}
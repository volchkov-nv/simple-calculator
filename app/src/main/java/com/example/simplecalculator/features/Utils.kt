package com.example.simplecalculator.features

import com.example.simplecalculator.calculator.OperatorState

object Utils {

    fun checkValueCorrectness(textValue: String, currentNumber: Char) : Boolean {
        if (textValue.contains('.') && currentNumber == '.'){
            return false
        }
        return true
    }

    fun firstZeroNumberCheck(textValue: String, currentNumber: Char) : Boolean {
        if (textValue == "0" || textValue == "-0") {
            return currentNumber == '.'
        }
        return true
    }

    fun convertToNegative(textValue: String) : String {
        if (textValue.isEmpty()) return  textValue
        return if (textValue[0] == OperatorState.MINUS.toChar()) {
            textValue.drop(1)
        } else {
            OperatorState.MINUS.toChar().plus(textValue)
        }
    }

    fun convertMemoryValue(textValue: String) : String {
        if (textValue == "-0") return textValue.drop(1)
        return textValue
    }
}
package com.example.simplecalculator.features

object Utils {

    fun checkValueCorrectness(textValue: String, currentNumber: Char) : Boolean {
        if (textValue.contains('.') && currentNumber == '.'){
            return false
        }
        return true
    }

    fun firstZeroNumberCheck(textValue: String, currentNumber: Char) : Boolean {
        if (textValue == "0") {
            return currentNumber == '.'
        }
        return true
    }
}
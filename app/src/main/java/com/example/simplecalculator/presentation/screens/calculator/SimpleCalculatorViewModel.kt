package com.example.simplecalculator.presentation.screens.calculator

import androidx.lifecycle.MutableLiveData
import com.example.simplecalculator.app.navigation.Navigator
import com.example.simplecalculator.calculator.ErrorState
import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.presentation.base.BaseViewModel
import com.example.simplecalculator.presentation.base.onNext
import java.lang.Exception
import java.lang.StringBuilder
import javax.inject.Inject

class SimpleCalculatorViewModel @Inject constructor(
    private val calculator: SimpleCalculator,
    private val navigator: Navigator
): BaseViewModel(){

    val screenValueUpdate = MutableLiveData<Pair<String, String>>()
    val screenSizeUpdate = MutableLiveData<Boolean>()
    val operatorLock = MutableLiveData<Boolean>()

    private val firstValue : StringBuilder = StringBuilder()
    private val secondValue : StringBuilder = StringBuilder()
    private var operator: OperatorState = OperatorState.NONE
    private var errorState = ErrorState.NONE
    private var maxSize = 17

    fun updateMaxSize(size: Int) {
        maxSize = size
    }

    fun clear() {
        setInitialData()
        screenValueUpdate.onNext(Pair("", ""))
    }

    private fun setInitialData() {
        firstValue.clear()
        secondValue.clear()
        errorState = ErrorState.NONE
        operator = OperatorState.NONE
        operatorLock.onNext(true)
    }

    fun result() {
        if (firstValue.isNotEmpty() && secondValue.isNotEmpty() && operator != OperatorState.NONE) {
            if (secondValue.length == 1 && secondValue[0] == '-') return
            val result = calculateExpression()
            if (errorState == ErrorState.NONE) {
                setInitialData()
                firstValue.append(result)
                printValue(getOutputPair(firstValue.toString()))
            } else {
                printValue(getOutputPair(errorState.toString()))
                setInitialData()
            }
        }
    }

    fun backSpace() {
        if (secondValue.isNotEmpty()) {
            secondValue.deleteCharAt(secondValue.length - 1)
            if (secondValue.isNotEmpty()) {
                if (secondValue.length == 1 && secondValue[0] == '-') {
                    printValue(getOutputPair(firstValue.toString(), operator, secondValue.toString(), ""))
                } else {
                    printValue(getOutputPair(firstValue.toString(), operator, secondValue.toString(), calculateExpression()))
                }
            } else {
                printValue(getOutputPair(firstValue.toString(), operator))
            }
        } else if (operator != OperatorState.NONE) {
            operator = OperatorState.NONE
            printValue(getOutputPair(firstValue.toString()))
        } else if (firstValue.isNotEmpty()) {
            firstValue.deleteCharAt(firstValue.length - 1)
            if (firstValue.isNotEmpty()) {
                printValue(getOutputPair(firstValue.toString()))
            } else {
                clear()
            }
        }
    }

    fun numberAction(number : Char) {
        if (operator == OperatorState.NONE) {
            addFirstNumber(number)
        } else {
            addSecondNumber(number)
        }
    }

    private fun addFirstNumber(number : Char) {
        if (firstValue.length < maxSize
            && checkValueCorrectness(firstValue.toString(), number)) {

            if (firstZeroNumberCheck(firstValue.toString(), number)) {
                firstValue.append(number)
            } else {
                firstValue[0] = number
            }
            printValue(getOutputPair(firstValue.toString()))
        }
    }

    private fun addSecondNumber(number : Char) {
        if (secondValue.length < maxSize
            && checkValueCorrectness(secondValue.toString(), number)) {

            if (firstZeroNumberCheck(secondValue.toString(), number)) {
                secondValue.append(number)
            } else {
                secondValue[0] = number
            }

            if (secondValue.length == 1 && secondValue[0] == '-') {
                printValue(getOutputPair(firstValue.toString(),
                    operator, secondValue.toString(), ""))
            } else {
                printValue(getOutputPair(firstValue.toString(),
                    operator, secondValue.toString(), calculateExpression()))
            }
        }
    }

    private fun printValue(pair: Pair<String, String>) {
        screenValueUpdate.onNext(pair)
    }

    private fun calculateExpression() : String {
        return if ((operator == OperatorState.DIVIDE || operator == OperatorState.REMAINDER) && secondValue.toString() == "0") {
            errorState = ErrorState.ZERO_DIVISION
            operatorLock.onNext(false)
            ErrorState.ZERO_DIVISION.toString()
        } else {
            calculateValue(operator, firstValue.toString().toDouble(),
                secondValue.toString().toDouble()).toString().removeLastZero()
        }
    }

    private fun checkValueCorrectness(textValue: String, currentNumber: Char) : Boolean {
        if (textValue.contains('.') && currentNumber == '.'){
            return false
        }
        return true
    }

    private fun firstZeroNumberCheck(textValue: String, currentNumber: Char) : Boolean {
        if (textValue.length == 1 && textValue == "0") {
            return currentNumber == '.'
        }
        return true
    }

    fun operatorAction(symbol : Char) {
        if ((firstValue.isEmpty() && symbol == '-') || (secondValue.isEmpty() && operator != OperatorState.NONE && symbol == '-')) {
            if (operator == OperatorState.MINUS) return
            numberAction(symbol)
            return
        }
        if (operator == OperatorState.NONE) {
            if (firstValue.isEmpty() || (firstValue.length == 1 && firstValue[0] == '-')) return
            addOperator(symbol)
        } else {
            updateOperator(symbol)
        }
    }

    private fun addOperator(symbol : Char) {
        operator = OperatorState.getStateByChar(symbol)
        printValue(getOutputPair(firstValue.toString(), operator))
    }

    private fun updateOperator(symbol : Char) {
        if (secondValue.isNotEmpty()) {
            if (secondValue.length == 1 && secondValue[0] == '-') {
                if (symbol == '+') {
                    backSpace()
                } else if (symbol != '-') {
                    backSpace()
                    updateAndPrintNewOperator(symbol)
                }
                return
            }
            val result = calculateExpression()
            operator = OperatorState.getStateByChar(symbol)
            updateValuesAfterCalculation(result)
            printValue(getOutputPair(result, operator))
        } else {
            updateAndPrintNewOperator(symbol)
        }
    }

    private fun updateAndPrintNewOperator(symbol: Char) {
        operator = OperatorState.getStateByChar(symbol)
        printValue(getOutputPair(firstValue.toString(), operator))
    }

    private fun updateValuesAfterCalculation(result: String) {
        firstValue.clear()
        secondValue.clear()
        firstValue.append(result)
    }

    private fun calculateValue(operator: OperatorState, num1: Double, num2: Double) : Double  {
        return  when (operator) {
            OperatorState.PLUS -> calculator.add(num1, num2)
            OperatorState.MINUS -> calculator.subtract(num1, num2)
            OperatorState.MULTIPLY -> calculator.multiply(num1, num2)
            OperatorState.DIVIDE -> calculator.divide(num1, num2)
            OperatorState.REMAINDER -> calculator.remainder(num1, num2)
            OperatorState.NONE -> throw Exception("operator cant be NONE")
        }
    }

    private fun getOutputPair(firstVal: String, operator: OperatorState) : Pair<String, String> {
        return Pair(StringBuilder(firstVal).append(" ").append(operator.toString()).toString(), "")
    }

    private fun getOutputPair(firstVal: String) : Pair<String, String> {
        return Pair(firstVal, "")
    }

    private fun getOutputPair(firstVal: String, operator: OperatorState, secondVal: String, result: String) : Pair<String, String> {
        return Pair(StringBuilder(firstVal).append(" ").append(operator.toString())
            .append(" ").append(secondVal).toString()
            , result)
    }

    private fun String.removeLastZero(): String {
        if (this.length > 1 && this.indexOf('.') == this.length - 2 && this.last() == '0') {
            return this.substring(0, this.length - 2)
        }
        return this
    }


}


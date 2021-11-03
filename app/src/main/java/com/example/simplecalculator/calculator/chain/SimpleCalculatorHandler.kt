package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils
import timber.log.Timber
import java.lang.StringBuilder

abstract class SimpleCalculatorHandler(
    private val repository: SimpleCalculatorRepository,
    private val outputAction: (OperationModel) -> Unit
) {

    private var nextHandler: SimpleCalculatorHandler? = null
    protected var operationModel : OperationModel = repository.getCurrentState()

    fun addNextHandler(handler: SimpleCalculatorHandler) {
        nextHandler = handler
    }

    abstract fun addValue(char: Char, type: ValueType)

    open fun addValue(type: ValueType) {
        if (type == ValueType.BACKSPACE) {
            addValue(' ', ValueType.BACKSPACE)
        } else {
            nextHandler?.addValue(type)
        }
    }

    protected open fun updateState() {
        operationModel = repository.getCurrentState()
    }

    protected fun addNewNumber(char: Char, line: String ) : String {
        val builder = StringBuilder(line)
        if (Utils.firstZeroNumberCheck(line, char)) {
            builder.append(char)
        } else {
            builder.clear()
            builder.append(char)
        }
        return builder.toString()
    }

    protected fun String.removeLastZero(): String {
        if (this.length > 1 && this.indexOf('.') == this.length - 2 && this.last() == '0') {
            return this.substring(0, this.length - 2)
        }
        return this
    }

    protected fun doNext(char: Char, type: ValueType) {
        nextHandler?.addValue(char, type)
    }

    protected fun sendToPrint(state: OperationModel) {
        Timber.d("send to print: v1 = ${state.firstValue}, operator = ${state.operator}, " +
                "v2 = ${state.secondValue}, r = ${operationModel.result}")
        outputAction.invoke(state)
    }

    protected fun getCurrentState() : OperationModel {
        return repository.getCurrentState()
    }

    abstract fun backSpace(char: Char, type: ValueType)

}
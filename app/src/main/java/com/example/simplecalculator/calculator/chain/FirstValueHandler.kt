package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils
import timber.log.Timber

class FirstValueHandler(
    private val repository: SimpleCalculatorRepository,
    private val outputAction: (OperationModel) -> Unit
) : SimpleCalculatorHandler(repository, outputAction) {


    override fun addValue(char: Char, type: ValueType) {
        updateState()
        when(type) {
            ValueType.VALUE -> checkOperator(char, type)
            ValueType.OPERATOR -> doTypeOperator(char, type)
            ValueType.CLEAN -> doNext(char, type)
            ValueType.RESULT -> doNext(char, type)
            ValueType.BACKSPACE -> backSpace(char, type)
        }
        Timber.d("value $char added, type $type")
    }

    private fun checkOperator(char: Char, type: ValueType) {
        if (operationModel.operator.isEmpty()) {
            doTypeValue(char)
        } else {
            doNext(char, type)
        }
    }

    private fun doTypeValue(number: Char) {
        if (Utils.checkValueCorrectness(operationModel.firstValue, number)) {
            sendToPrint(getCurrentState().copy(
                firstValue = addNewNumber(number, operationModel.firstValue)
            ))
        }
    }

    private fun doTypeOperator(char: Char, type: ValueType) {
        if (operationModel.firstValue.isEmpty()) {
            if (char == OperatorState.MINUS.toChar()) {
                doTypeValue(char)
            }
        } else {
            if (operationModel.firstValue != OperatorState.MINUS.toString()) {
                doNext(char, type)
            }
        }
    }

    override fun backSpace(char: Char, type: ValueType) {
        if (operationModel.operator.isEmpty()) {
            if (operationModel.firstValue.isNotEmpty()) {
                sendToPrint(operationModel.copy(
                    firstValue = operationModel.firstValue.dropLast(1)
                ))
            }
        } else {
            doNext(char, type)
        }
    }
}
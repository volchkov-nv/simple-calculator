package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils

class SecondValueHandler (
    private val repository: SimpleCalculatorRepository,
    private val outputAction: (OperationModel) -> Unit
) : SimpleCalculatorHandler(repository, outputAction) {


    override fun addValue(char: Char, type: ValueType) {
        updateState()
        when(type) {
            ValueType.VALUE -> doTypeValue(char, type)
            ValueType.OPERATOR -> operatorAction(char, type)
            ValueType.CLEAN -> doNext(char, type)
            ValueType.RESULT -> doNext(char, type)
            ValueType.BACKSPACE -> backSpace(char, type)
        }
    }

    private fun operatorAction(char: Char, type: ValueType) {
        if (operationModel.secondValue.isEmpty() && char == OperatorState.MINUS.toChar()) {
            sendValue(char)
        } else {
            doNext(char, type)
        }
    }

    private fun sendValue(char: Char) {
        sendToPrint(getCurrentState().copy(
            secondValue = addNewNumber(char, operationModel.secondValue)
        ))
    }

    private fun doTypeValue(char: Char, type: ValueType) {
        if (Utils.checkValueCorrectness(operationModel.secondValue, char)) {
            sendValue(char)
            doNext(char, type)
        }
    }

    override fun backSpace(char: Char, type: ValueType) {
        if (operationModel.secondValue.isNotEmpty()) {
            val newValue = operationModel.secondValue.dropLast(1)
            if (newValue.isNotEmpty() && newValue != OperatorState.MINUS.toString()) {
                sendToPrint(operationModel.copy(
                    secondValue = newValue
                ))
                doNext(char, type)
            } else {
                sendToPrint(operationModel.copy(
                    secondValue = newValue,
                    result = ""
                ))
            }
        }
    }

}
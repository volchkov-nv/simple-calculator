package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository

class OperatorHandler (
    private val repository: SimpleCalculatorRepository,
    private val outputAction: (OperationModel) -> Unit
) : SimpleCalculatorHandler(repository, outputAction) {

    private var operator: OperatorState

    init {
        operator = operationModel.operator.toOperator()
    }

    override fun updateState() {
        super.updateState()
        operator = operationModel.operator.toOperator()
    }

    override fun addValue(char: Char, type: ValueType) {
        updateState()
        when(type) {
            ValueType.VALUE -> doNext(char, type)
            ValueType.OPERATOR -> operatorAction(char, type)
            ValueType.CLEAN -> doNext(char, type)
            ValueType.RESULT -> doNext(char, type)
            ValueType.BACKSPACE -> backSpace(char, type)
        }
    }

    private fun operatorAction(char: Char, type: ValueType) {
        if (operator == OperatorState.NONE) {
            addNewOperator(char)
        } else {
            updateOperator(char, type)
        }
    }

    private fun updateOperator(char: Char, type: ValueType) {
        if (operationModel.secondValue.isEmpty()) {
            secondEmptyAction(char, type)
        } else {
            secondNotEmptyAction(char, type)
        }
    }

    private fun secondNotEmptyAction(char: Char, type: ValueType) {
        if (operationModel.secondValue == OperatorState.MINUS.toString()) {
            if (operator != OperatorState.MINUS) {
                updateOperatorAndSecond(char)
            }
        } else {
            doNext(char, type)
        }
    }

    private fun secondEmptyAction(char: Char, type: ValueType) {
        if (OperatorState.getStateByChar(char) == OperatorState.MINUS) {
            minusAction(char, type)
        } else {
            addNewOperator(char)
        }
    }

    private fun minusAction(char: Char, type: ValueType) {
        if (operator == OperatorState.MULTIPLY || operator == OperatorState.DIVIDE) {
            doNext(char, type)
        } else {
            addNewOperator(char)
        }
    }

    private fun updateOperatorAndSecond(char : Char) {
        operator = OperatorState.getStateByChar(char)
        sendToPrint(operationModel.copy(
            operator = char.toString(),
            secondValue = "")
        )
    }

    private fun addNewOperator(char : Char) {
        operator = OperatorState.getStateByChar(char)
        sendToPrint(operationModel.copy(operator = char.toString()))
    }

    private fun String.toOperator() : OperatorState {
        return if (this.length == 1) {
            OperatorState.getStateByChar(this[0])
        } else {
            OperatorState.NONE
        }
    }

    override fun backSpace(char: Char, type: ValueType) {
        if (operationModel.secondValue.isEmpty()) {
            if (operationModel.operator.isNotEmpty()) {
                sendToPrint(operationModel.copy(
                    operator = operationModel.operator.dropLast(1)
                ))
            }
        } else {
            doNext(char, type)
        }
    }

}
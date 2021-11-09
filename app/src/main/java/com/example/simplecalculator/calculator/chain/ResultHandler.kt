package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.ErrorState
import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.domain.models.CalcModel
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils
import timber.log.Timber
import java.lang.Exception

class ResultHandler (
    private val repository: SimpleCalculatorRepository,
    private val calculator: SimpleCalculator,
    private val outputAction: (OperationModel) -> Unit,
    private val memoryAction: (Boolean) -> Unit
) : SimpleCalculatorHandler(repository, outputAction, memoryAction) {

    override fun addValue(char: Char, type: ValueType) {
        actionByType(type, char)
    }

    override fun addValue(type: ValueType) {
        actionByType(type)
    }

    private fun actionByType(type: ValueType, char: Char = ' ') {
        updateState()
        when (type) {
            ValueType.VALUE -> result()
            ValueType.OPERATOR -> resultWithNewAction(char, type)
            ValueType.RESULT -> resultWithNewValue()
            ValueType.CLEAN -> cleanAction()
            ValueType.BACKSPACE -> result()
        }
        Timber.d("value $char added, type $type")
    }

    override fun showMemory(type: MemoryType) {
        result()
    }

    override fun addPositiveMemory(type: MemoryType) {
        if (operationModel.result.isNotEmpty()) {
            saveNewMemory(operationModel.result)
        }
    }

    override fun addNegativeMemory(type: MemoryType) {
        if (operationModel.result.isNotEmpty()) {
            saveNewMemory(Utils.convertToNegative(operationModel.result))
        }
    }

    private fun cleanAction() {
        sendToPrint(OperationModel.getNew())
    }

    private fun resultWithNewValue() {
        if (operationModel.result.isNotEmpty() && operationModel.error.isEmpty()) {
            sendToPrint(OperationModel.getNew().copy(
                firstValue = operationModel.result
            ))
        }
    }

    private fun resultWithNewAction(char: Char, type: ValueType) {
        if (operationModel.error.isEmpty()) {
            sendToPrint(OperationModel.getNew().copy(
                firstValue = operationModel.result,
                operator = char.toString()
            ))
        }
    }

    private fun result() {
        var calcModel : CalcModel? = null
        if (isModelCorrect()) {
            try {
                calcModel = operationModel.convert()
            } catch (e : Exception) {
                e.printStackTrace()
                Timber.e("Convert error, ${e.message}")
            }
            calcModel?.let { cModel->
                sendToPrint(cModel.calculate().getOutput())
            }
        }

    }

    private fun CalcModel.calculate() : CalcModel {
        return if ((this.operator == OperatorState.DIVIDE || this.operator == OperatorState.REMAINDER)
            && this.secondValue == 0.0) {
            this.copy(error = ErrorState.ZERO_DIVISION)
        } else {
            try {
                this.copy(result = this.calculateValue())
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.e("Calculation error, ${e.message}")
                this
            }
        }
    }

    private fun CalcModel.calculateValue() : Double  {
        return  when (this.operator) {
            OperatorState.PLUS -> calculator.add(this.firstValue, this.secondValue)
            OperatorState.MINUS -> calculator.subtract(this.firstValue, this.secondValue)
            OperatorState.MULTIPLY -> calculator.multiply(this.firstValue, this.secondValue)
            OperatorState.DIVIDE -> calculator.divide(this.firstValue, this.secondValue)
            OperatorState.REMAINDER -> calculator.remainder(this.firstValue, this.secondValue)
            OperatorState.NONE -> throw Exception("operator cant be NONE")
        }
    }

    override fun backSpace(char: Char, type: ValueType) {

    }

    private fun isModelCorrect() : Boolean {
        return operationModel.firstValue.isNotEmpty()
                && operationModel.secondValue.isNotEmpty()
                && operationModel.operator.isNotEmpty()
                && operationModel.operator.length == 1
    }

    private fun OperationModel.convert(
    ) : CalcModel {
        return CalcModel(
            firstValue = this.firstValue.toDouble(),
            operator = OperatorState.getStateByChar(this.operator[0]),
            secondValue = this.secondValue.toDouble(),
            result = null,
            error = ErrorState.NONE
        )
    }

    private fun CalcModel.getOutput(
    ) : OperationModel {
        return operationModel.copy(
            result = this.result.toString().removeLastZero(),
            error = this.error.toString()
        )

    }

}
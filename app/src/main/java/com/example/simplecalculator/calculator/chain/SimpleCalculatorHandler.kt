package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils
import timber.log.Timber
import java.lang.StringBuilder

abstract class SimpleCalculatorHandler(
    private val repository: SimpleCalculatorRepository,
    private val outputAction: (OperationModel) -> Unit,
    private val memoryAction: (Boolean) -> Unit
) {

    private var nextHandler: SimpleCalculatorHandler? = null
    protected var operationModel : OperationModel = repository.getCurrentState()

    fun addNextHandler(handler: SimpleCalculatorHandler) {
        nextHandler = handler
    }

    abstract fun addValue(char: Char, type: ValueType)

    fun doMemory(type: MemoryType) {
        updateState()
        when(type) {
            MemoryType.SHOW -> showMemory(type)
            MemoryType.DELETE -> deleteMemory(type)
            MemoryType.ADD_POSITIVE -> addPositiveMemory(type)
            MemoryType.ADD_NEGATIVE -> addNegativeMemory(type)
        }
    }

    protected open fun addPositiveMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    protected open fun addNegativeMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    protected open fun showMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    protected open fun deleteMemory(type: MemoryType) {
        repository.clearMemory()
        memoryAction.invoke(false)
    }

    protected fun doNextMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    protected fun saveNewMemory(text: String) {
        repository.setMemory(text)
        memoryAction.invoke(true)
    }

    protected fun getOutputValueWithMemory(memory: String, value: String) : String {
        return if (value == OperatorState.MINUS.toString()) {
            if (memory.isNotEmpty() && memory[0] == OperatorState.MINUS.toChar()) {
                memory.drop(1)
            } else {
                memory
            }
        } else {
            memory
        }
    }

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
            if (line.isNotEmpty() && line[0] == OperatorState.MINUS.toChar()) {
                builder.append(OperatorState.MINUS.toChar())
            }
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
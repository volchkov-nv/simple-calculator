package com.example.simplecalculator.calculator.chain

import com.example.simplecalculator.calculator.OperatorState
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.features.Utils
import timber.log.Timber
import java.lang.StringBuilder

/**
 * This class implement main logic to control calculation process
 * and send current state to print on screen.
 * The class based on Chain of Responsibility design pattern.
 * [SimpleCalculatorHandler] is an abstraction for group of handlers which linked to each other.
 * Chain design looks like:
 * [FirstValueHandler] -> [OperatorHandler] -> [SecondValueHandler] -> [ResultHandler]
 * Where "->" means input function [addValue] or [doMemory]
 * Each handler is responsible for his element on the screen.
 *
 * Current state of each handlers stored in [operationModel]. It should be updated each time
 * the handler is used via [updateState]
 *
 * @constructor:
 * [repository] - repository for all data which is necessary for handlers
 * [outputAction] - function of sending the current state to print
 * [memoryAction] - function of updating memory icon on the screen
 *
 * @methods:
 * [addValue] - main input function. This function called from previous handler or from outside
 * in [FirstValueHandler] It checks type of operation [ValueType] and do some action depends on it.
 * If current calculation process isn't over - call [addValue] of next handler.
 *
 * [doMemory] - memory input function. It checks type [MemoryType] and do some action depending on this
 *
 * [addNextHandler] - create link to the next handler
 *
 * [sendToPrint] - function to send state
 */
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

    /**
     * Save current handler's value in memory as is.
     */
    protected open fun addPositiveMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    /**
     * Save current handler's value in memory and change sign of value
     */
    protected open fun addNegativeMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    /**
     * Set current memory value to the handler and update screen
     */
    protected open fun showMemory(type: MemoryType) {
        nextHandler?.doMemory(type)
    }

    /**
     * Clear stored memory
     */
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
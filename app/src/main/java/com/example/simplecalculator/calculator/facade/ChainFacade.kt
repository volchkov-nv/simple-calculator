package com.example.simplecalculator.calculator.facade

import com.example.simplecalculator.calculator.chain.MemoryType
import com.example.simplecalculator.calculator.chain.SimpleCalculatorHandler
import com.example.simplecalculator.calculator.chain.ValueType
import com.example.simplecalculator.domain.models.OperationModel

/**
 * This class is facade for [SimpleCalculatorHandler] and hide logic of its initialization.
 *
 */
interface ChainFacade {

    /**
     * Create instance of handlers and links between them
     */
    fun initChain(action: (OperationModel) -> Unit, actionMemory: (Boolean) -> Unit)

    /**
     * This function calls each time when calculator receive new value or new operation from user
     */
    fun newValue(char: Char, valueType: ValueType)

    /**
     * Short version of [newValue]. It calls when action doesn't have any chars.
     */
    fun newValue(valueType: ValueType)

    /**
     * Function for all operation with memory
     */
    fun memoryAction(type: MemoryType)
}
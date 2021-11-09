package com.example.simplecalculator.calculator.facade

import com.example.simplecalculator.calculator.chain.MemoryType
import com.example.simplecalculator.calculator.chain.ValueType
import com.example.simplecalculator.domain.models.OperationModel

interface ChainFacade {

    fun initChain(action: (OperationModel) -> Unit, actionMemory: (Boolean) -> Unit)

    fun newValue(char: Char, valueType: ValueType)

    fun newValue(valueType: ValueType)

    fun memoryAction(type: MemoryType)
}
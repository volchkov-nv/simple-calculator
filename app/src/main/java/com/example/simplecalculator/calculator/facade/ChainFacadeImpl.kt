package com.example.simplecalculator.calculator.facade

import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.calculator.chain.*
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import javax.inject.Inject

class ChainFacadeImpl @Inject constructor(
    private val repository: SimpleCalculatorRepository,
    private val calculator: SimpleCalculator
) : ChainFacade {

    private var firstValueHandler : FirstValueHandler? = null
    private var secondValueHandler : SecondValueHandler? = null
    private var operatorHandler : OperatorHandler? = null
    private var resultHandler : ResultHandler? = null

    override fun initChain(action: (OperationModel) -> Unit,
    actionMemory: (Boolean) -> Unit) {
        firstValueHandler = FirstValueHandler(repository, action, actionMemory)
        secondValueHandler = SecondValueHandler(repository, action, actionMemory)
        operatorHandler = OperatorHandler(repository, action, actionMemory)
        resultHandler = ResultHandler(repository, calculator, action, actionMemory)
        createLinks()
    }

    private fun createLinks() {
        firstValueHandler?.addLink(operatorHandler)
        operatorHandler?.addLink(secondValueHandler)
        secondValueHandler?.addLink(resultHandler)
    }

    private fun SimpleCalculatorHandler.addLink(handler: SimpleCalculatorHandler?) {
        handler?.let { this.addNextHandler(handler) }
    }

    override fun newValue(char: Char, valueType: ValueType) {
        firstValueHandler?.addValue(char, valueType)
    }

    override fun newValue(valueType: ValueType) {
        firstValueHandler?.addValue(valueType)
    }

    override fun memoryAction(type: MemoryType) {
        firstValueHandler?.doMemory(type)
    }
}
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
    private var isInitialized = false

    override fun initChain(action: (OperationModel) -> Unit) {
        if (isInitialized) return
        firstValueHandler = FirstValueHandler(repository, action)
        secondValueHandler = SecondValueHandler(repository, action)
        operatorHandler = OperatorHandler(repository, action)
        resultHandler = ResultHandler(repository, calculator, action)
        createLinks()

        isInitialized = true
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
}
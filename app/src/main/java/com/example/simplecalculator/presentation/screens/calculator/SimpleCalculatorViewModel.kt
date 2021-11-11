package com.example.simplecalculator.presentation.screens.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.calculator.chain.*
import com.example.simplecalculator.calculator.facade.ChainFacade
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.presentation.base.BaseViewModel
import com.example.simplecalculator.presentation.base.onNext
import javax.inject.Inject

class SimpleCalculatorViewModel @Inject constructor(
    private val facade: ChainFacade,
    private val repository: SimpleCalculatorRepository
): BaseViewModel(){

    val operatorLock = MutableLiveData<Boolean>()
    val screenStateUpdate = MutableLiveData<OperationModel>()
    val screenMemoryUpdate = MutableLiveData<Boolean>()

    init {
        facade.initChain(::receiver, ::updateMemoryUi)
    }

    fun checkMemoryAndUpdate() {
        updateMemoryUi(!repository.isMemoryEmpty())
    }

    private fun updateMemoryUi(isShow: Boolean) {
        screenMemoryUpdate.onNext(isShow)
    }

    fun updateScreenState() {
        screenStateUpdate.onNext(repository.getCurrentState())
    }

    private fun chainAction(char: Char, type: ValueType) {
        facade.newValue(char, type)
    }

    private fun chainAction(type: ValueType) {
        facade.newValue(type)
    }

    private fun receiver(model: OperationModel) {
        repository.updateCurrentState(model)
        screenStateUpdate.onNext(model.copy())
        model.saveToDB()
    }

    private fun OperationModel.saveToDB() {
        if (this.isReadyToSave()) {
            repository.setNewHistoryData(
                this,
                onSuccess = {},
                onError = {},
                viewModelScope)
        }
    }

    fun clear() {
        chainAction(ValueType.CLEAN)
    }

    fun result() {
        chainAction(ValueType.RESULT)
    }

    fun backSpace() {
        chainAction(ValueType.BACKSPACE)
    }

    fun numberAction(number : Char) {
        chainAction(number, ValueType.VALUE)
    }

    fun operatorAction(symbol : Char) {
        chainAction(symbol, ValueType.OPERATOR)
    }

    fun setPositiveMemory() {
        facade.memoryAction(MemoryType.ADD_POSITIVE)
    }

    fun setNegativeMemory() {
        facade.memoryAction(MemoryType.ADD_NEGATIVE)
    }

    fun clearMemory() {
        facade.memoryAction(MemoryType.DELETE)
    }

    fun showMemory() {
        facade.memoryAction(MemoryType.SHOW)
    }
}


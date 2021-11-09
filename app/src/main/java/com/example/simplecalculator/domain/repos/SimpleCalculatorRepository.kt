package com.example.simplecalculator.domain.repos

import com.example.simplecalculator.domain.models.OperationModel
import kotlinx.coroutines.CoroutineScope

interface SimpleCalculatorRepository {


    fun getCurrentState() : OperationModel

    fun updateCurrentState(state : OperationModel)

    fun getAllHistoryData(onSuccess: (List<OperationModel>) -> Unit, scope: CoroutineScope)

    fun setNewHistoryData(model: OperationModel, onSuccess: (Long) -> Unit, onError: () -> Unit, scope: CoroutineScope)

    fun clearAllHistory(scope: CoroutineScope)

    fun clearHistoryById(id: Long, scope: CoroutineScope)

    fun getMemory() : String

    fun setMemory(text: String)

    fun clearMemory()

    fun isMemoryEmpty() : Boolean
}
package com.example.simplecalculator.domain.repos

import com.example.simplecalculator.domain.models.HistoryModel
import com.example.simplecalculator.domain.models.OperationModel
import kotlinx.coroutines.CoroutineScope

interface SimpleCalculatorRepository {


    fun getCurrentState() : OperationModel

    fun updateCurrentState(state : OperationModel)

    fun getAllHistoryData(onSuccess: (List<HistoryModel>) -> Unit, scope: CoroutineScope)

    fun setNewHistoryData(model: HistoryModel, onSuccess: (Long) -> Unit, onError: () -> Unit, scope: CoroutineScope)

    fun clearAllHistory(scope: CoroutineScope)

}
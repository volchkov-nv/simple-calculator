package com.example.simplecalculator.domain.repos

import com.example.simplecalculator.domain.models.HistoryModel

interface SimpleCalculatorRepository {

    fun getAllHistoryData(onSuccess: (List<HistoryModel>) -> Unit)

    fun setNewHistoryData(model: HistoryModel, onSuccess: (Long) -> Unit, onError: () -> Unit)

    fun clearAllHistory()

}
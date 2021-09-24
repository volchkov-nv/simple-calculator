package com.example.simplecalculator.domain.repos

import com.example.simplecalculator.domain.models.HistoryModel

interface SimpleCalculatorRepository {

    fun getAllHistoryData() : List<HistoryModel>

    fun setNewHistoryData(model: HistoryModel) : Long

    fun clearAllHistory()

}
package com.example.simplecalculator.data.converters

import com.example.simplecalculator.calculator.ErrorState
import com.example.simplecalculator.data.entity.HistoryEntity
import com.example.simplecalculator.domain.models.HistoryModel
import com.example.simplecalculator.domain.models.OperationModel

object CalculatorConverter {

    fun convertHistoryEntityToModel(
        entity: HistoryEntity
    ) : OperationModel {
        return OperationModel(
            id = entity.id.toString(),
            firstValue = entity.firstValue,
            secondValue = entity.secondValue,
            operator = entity.operator,
            result = entity.result,
            error = ""
        )
    }

    fun convertOperationModelToEntity(
        model: OperationModel
    ) : HistoryEntity {
        return HistoryEntity(
            id = null,
            firstValue = model.firstValue,
            secondValue = model.secondValue,
            operator = model.operator,
            result = model.result
        )
    }


}
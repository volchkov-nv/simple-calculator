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
            firstValue = entity.firstValue,
            secondValue = entity.secondValue,
            operator = entity.operator,
            result = entity.result,
            error = ""
        )
    }

    fun convertHistoryModelToEntity(
        model: HistoryModel
    ) : HistoryEntity {
        return HistoryEntity(
            id = model.id,
            firstValue = model.firstValue,
            secondValue = model.secondValue,
            operator = model.operator,
            result = model.result
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
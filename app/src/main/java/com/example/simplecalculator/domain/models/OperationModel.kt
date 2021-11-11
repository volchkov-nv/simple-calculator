package com.example.simplecalculator.domain.models

data class OperationModel (
    val id: String,
    val firstValue: String,
    val secondValue: String,
    val operator: String,
    val result: String,
    val error: String
) {
    companion object {
        fun getNew() : OperationModel = OperationModel("","","","", "", "")

    }

    fun isReadyToSave() : Boolean {
        return firstValue.isNotEmpty()
                && secondValue.isNotEmpty()
                && operator.isNotEmpty()
                && result.isNotEmpty()
                && error.isEmpty()
    }
}
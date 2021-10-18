package com.example.simplecalculator.domain.models

data class OperationModel (
    val firstValue: String,
    val secondValue: String,
    val operator: String,
    val result: String,
    val error: String
) {
    companion object {
        fun getNew() : OperationModel = OperationModel("","","","", "")
    }
}
package com.example.simplecalculator.domain.models

import com.example.simplecalculator.calculator.ErrorState
import com.example.simplecalculator.calculator.OperatorState

data class CalcModel(
    val firstValue: Double,
    val secondValue: Double,
    val operator: OperatorState,
    val result: Double?,
    val error: ErrorState
)
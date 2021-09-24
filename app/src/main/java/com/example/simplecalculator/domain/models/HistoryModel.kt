package com.example.simplecalculator.domain.models

data class HistoryModel(
    val id : Long,
    val firstValue: Double,
    val secondValue: Double,
    val operator: Char,
    val result: Double
)
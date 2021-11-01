package com.example.simplecalculator.domain.models

data class HistoryModel(
    val id : Long?,
    val firstValue: String,
    val secondValue: String,
    val operator: String,
    val result: String
)
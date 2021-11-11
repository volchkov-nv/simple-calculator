package com.example.simplecalculator.calculator

enum class ErrorState(private val message: String) {
    ZERO_DIVISION("Error - division by 0"),
    NONE("");

    override fun toString(): String {
        return message
    }
}
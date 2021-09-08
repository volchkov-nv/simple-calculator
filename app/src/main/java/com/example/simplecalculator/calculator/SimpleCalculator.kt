package com.example.simplecalculator.calculator

interface SimpleCalculator {

    fun add(number1: Double, number2: Double) : Double

    fun subtract(number1: Double, number2: Double) : Double

    fun multiply(number1: Double, number2: Double) : Double

    fun divide(number1: Double, number2: Double) : Double

    fun remainder(number1: Double, number2: Double) : Double
}
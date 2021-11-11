package com.example.simplecalculator.calculator

class SimpleCalculatorImpl : SimpleCalculator {

    override fun add(number1: Double, number2: Double): Double {
        return number1 + number2
    }

    override fun subtract(number1: Double, number2: Double): Double {
        return number1 - number2
    }

    override fun multiply(number1: Double, number2: Double): Double {
        return number1 * number2
    }

    override fun divide(number1: Double, number2: Double): Double {
        return number1 / number2
    }

    override fun remainder(number1: Double, number2: Double): Double {
        return number1 % number2
    }
}
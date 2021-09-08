package com.example.simplecalculator.calculator

enum class OperatorState(private val symbol: Char) {
    NONE('?'),
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    REMAINDER('%');

    override fun toString(): String {
        return symbol.toString()
    }

    companion object {

        fun getStateByChar(c: Char) : OperatorState {
            values().forEach {
                if (it.symbol == c)
                    return it
            }
            return NONE
        }
    }
}
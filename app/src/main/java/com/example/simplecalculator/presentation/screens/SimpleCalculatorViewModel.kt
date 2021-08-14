package com.example.simplecalculator.presentation.screens

import com.example.simplecalculator.app.navigation.Navigator
import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.presentation.base.BaseViewModel
import javax.inject.Inject

class SimpleCalculatorViewModel @Inject constructor(
    private val calculator: SimpleCalculator,
    private val navigator: Navigator
): BaseViewModel(){

}


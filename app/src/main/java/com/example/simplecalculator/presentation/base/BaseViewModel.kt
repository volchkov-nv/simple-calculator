package com.example.simplecalculator.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val onSuccess = MutableLiveData<Boolean>()

    protected fun onBackPressed() {

    }
}
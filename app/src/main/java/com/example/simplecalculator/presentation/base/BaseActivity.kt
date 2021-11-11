package com.example.simplecalculator.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    protected inline fun <reified T : BaseViewModel> injectViewModel(crossinline inject: () -> T): T {
        return ViewModelProvider(this, viewModelFactory(inject))[T::class.java]
    }

    protected fun <T> LiveData<T?>.subscribe(action: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer { data -> data?.let { action.invoke(it) } })
    }
}
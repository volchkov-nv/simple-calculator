package com.example.simplecalculator.presentation.widgets

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.StyleableRes

fun ViewGroup.obtainAttrs(
    attrs: AttributeSet?, @StyleableRes attrsId: IntArray,
    callback: (array: TypedArray) -> Unit
) {
    val array = context.obtainStyledAttributes(attrs, attrsId)
    callback.invoke(array)
    array.recycle()
}
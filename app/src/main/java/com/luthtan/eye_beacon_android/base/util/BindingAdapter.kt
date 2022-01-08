package com.luthtan.eye_beacon_android.base.util

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object BindingAdapter {

    @BindingAdapter("errorText")
    @JvmStatic
    fun TextInputLayout.bindErrorText(errorText: Int) {
        if (errorText != 0) {
            error = context.getString(errorText)
        } else {
            error = null
        }
    }
}
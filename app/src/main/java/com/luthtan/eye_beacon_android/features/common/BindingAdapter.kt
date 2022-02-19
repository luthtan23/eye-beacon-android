package com.luthtan.eye_beacon_android.features.common

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.luthtan.eye_beacon_android.features.login.LoginListener

object BindingAdapter {

    @BindingAdapter("errorText")
    @JvmStatic
    fun TextInputEditText.bindErrorText(errorText: Int) {
        if (errorText != 0) {
            error = context.getString(errorText)
        } else {
            error = null
        }
    }

    @BindingAdapter(value = ["linkText", "listenerLink"])
    @JvmStatic
    fun TextView.bindMakeLinks(linkText: String, listener: LoginListener) {
//        this.linkTextColors = ContextCompat.getColor(this.context, R.color.linkTextColorLightMode)
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                listener.onClickLinkText(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(linkText, startIndexOfLink + 1)
//      if(startIndexOfLink == -1) continue // todo if you want to verify your texts contains links text
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + linkText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}
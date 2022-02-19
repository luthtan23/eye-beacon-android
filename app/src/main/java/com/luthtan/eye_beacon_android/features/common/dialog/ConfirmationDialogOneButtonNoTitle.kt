package com.luthtan.eye_beacon_android.features.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.luthtan.eye_beacon_android.databinding.DialogConfirmationOneButtonNoTitleBinding
import timber.log.Timber

class ConfirmationDialogOneButtonNoTitle : DialogFragment() {

    private var binding: DialogConfirmationOneButtonNoTitleBinding? = null
    var onLeftButtonClickListener: (DialogFragment, View) -> Unit = { _, _ ->}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NO_TITLE, 0)

        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(true)

        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        try {
            dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        } catch (e: Exception) {
            Timber.e(e)
        }

        binding = DialogConfirmationOneButtonNoTitleBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(arguments) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(ARGUMENT_DESCRIPTION)?.let { setDescription(it) }
        arguments?.getString(ARGUMENT_LEFT_BUTTON_TEXT)?.let { setLeftButtonText(it) }
        binding?.let {
            with(it) {
                btnLeft.setOnClickListener { onLeftButtonClickListener.invoke(this@ConfirmationDialogOneButtonNoTitle, view) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun setDescription(description: String) {
        binding?.desc?.text = description
    }

    fun setDescription(@StringRes description: Int) {
        binding?.desc?.setText(description)
    }

    fun setLeftButtonText(text: String) {
        binding?.btnLeft?.text = text
    }

    fun setLeftButtonText(@StringRes text: Int) {
        binding?.btnLeft?.setText(text)
    }

    companion object{
        private const val ARGUMENT_DESCRIPTION = "ARGUMENT_DESCRIPTION"
        private const val ARGUMENT_LEFT_BUTTON_TEXT = "ARGUMENT_LEFT_BUTTON_TEXT"

        fun newInstance(
            description: String,
            leftBtnText: String,
        ): ConfirmationDialogOneButtonNoTitle {
            return ConfirmationDialogOneButtonNoTitle().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_DESCRIPTION, description)
                    putString(ARGUMENT_LEFT_BUTTON_TEXT, leftBtnText)
                }
            }
        }
    }
}
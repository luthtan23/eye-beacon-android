package com.luthtan.eye_beacon_android.features.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.luthtan.eye_beacon_android.databinding.DialogLoadingBinding
import timber.log.Timber

class LoadingDialog : DialogFragment() {

    private var binding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NO_TITLE, 0)

        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        try {
            dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        } catch (e: Exception) {
            Timber.e(e)
        }

        binding = DialogLoadingBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(arguments) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "showLoading")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {

        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}
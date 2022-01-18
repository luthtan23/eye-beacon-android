package com.luthtan.eye_beacon_android.features.dashboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.luthtan.eye_beacon_android.databinding.DialogDashboardCountBinding
import timber.log.Timber

class DashboardDialog : DialogFragment() {

    private var binding: DialogDashboardCountBinding? = null

    private val viewModel: DashboardViewModel by viewModels({requireParentFragment()})

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

        binding = DialogDashboardCountBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setCountDown()

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.listener = viewModel

        viewModel.dismissDialog.observe(this, {
            it.getContentIfNotHandled()?.let {
                viewModel.setStopBeacon()
                dismiss()
            }
        })

        binding?.btnRight?.setOnClickListener {
            viewModel.setBreakCount(true)
            viewModel.setAutomaticallySignIn()
            dismiss()
        }

        binding?.btnLeft?.setOnClickListener {
            viewModel.setStopBeacon()
            dismiss()
        }
    }
}
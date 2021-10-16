package com.luthtan.eye_beacon_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {
    abstract val binding: VB
    abstract val viewModel: VM

    private var hasInitialized = false

    //add permission
    protected val permissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
        val granted = permission.entries.all {
            it.value == true
        }
        onRequestResult(granted)
    }

    protected open fun onRequestResult(granted: Boolean) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPress()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    protected fun onBackPress() {
        if (!findNavController().navigateUp()) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitialized) {
            onInitViews()
            onInitObservers()
        }
    }

    protected open fun onInitViews() = Unit

    protected open fun onInitObservers() = Unit

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        hasInitialized = true
        onInitPause()
    }

    protected open fun onInitPause() = Unit


}
package com.luthtan.eye_beacon_android.features.login

import android.Manifest
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.databinding.FragmentLoginBinding
import com.luthtan.eye_beacon_android.features.common.PERMISSION_LOCATION_FINE
import com.luthtan.eye_beacon_android.features.login.util.LOGIN_TO_REGISTER_LINK
import com.luthtan.simplebleproject.data.repository.PreferencesRepository
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel
        get() = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

    override val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val requestPermission by lazy {
        permissionRequestLauncher.launch(arrayOf(PERMISSION_LOCATION_FINE))
    }

    override fun onInitViews() {
        super.onInitViews()

        binding.viewModel = viewModel
        binding.listener = viewModel

        requestPermission
    }

    override fun onRequestResult(granted: Boolean) {
        super.onRequestResult(granted)
        if (!granted) {
            AlertLocationDialog.showRequestPermission(requireContext()) {
                permissionRequestLauncher.launch(arrayOf(PERMISSION_LOCATION_FINE))
            }
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.goToDashboard.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(LoginFragmentDirections.actionGoToDashboard())
            }
        })

        viewModel.goToRegister.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(LoginFragmentDirections.actionGoToRegister())
            }
        })
    }

}
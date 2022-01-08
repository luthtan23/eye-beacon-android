package com.luthtan.eye_beacon_android.features.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.base.util.AlertLocationDialog
import com.luthtan.eye_beacon_android.databinding.FragmentLoginBinding
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import com.luthtan.eye_beacon_android.features.common.PERMISSION_LOCATION_FINE
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import timber.log.Timber

@FragmentScoped
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

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

        viewModel.signInRoom()

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
            it.getContentIfNotHandled()?.let { loginPage ->
                var valid = true
                if (loginPage.eyeBle.isEmpty()) {
                    binding.etLoginUUID.error = getString(R.string.login_error_form)
                    valid = false
                }
                if (loginPage.localIP.isEmpty()) {
                    binding.etLoginLocalIP.error = getString(R.string.login_error_form)
                    valid = false
                }
                if (loginPage.username.isEmpty()) {
                    binding.etLoginUsername.error = getString(R.string.login_error_form)
                    valid = false
                }
                if (valid) {
                    findNavController().navigate(LoginFragmentDirections.actionGoToDashboard(loginPage))
                }
            }
        })

        viewModel.signInRoomResponse.observe(this, {
            when(it) {
                is ResultState.Loading -> {
                    showToast("Loading")
                }
                is ResultState.Success -> {
                    showToast(it.data.nameUser)
                }
                is ResultState.Error -> {
                    Timber.e(it.throwable)
                }
            }
        })

        /*viewModel.goToRegister.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(LoginFragmentDirections.actionGoToRegister())
            }
        })*/

    }



}
package com.luthtan.eye_beacon_android.features.login

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.base.util.AlertLocationDialog
import com.luthtan.eye_beacon_android.base.util.KeyboardUtil
import com.luthtan.eye_beacon_android.databinding.FragmentLoginBinding
import com.luthtan.eye_beacon_android.features.common.PERMISSION_LOCATION_FINE
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

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

        binding.lifecycleOwner = this
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
            it.getContentIfNotHandled()?.let { loginPage ->
                KeyboardUtil.hideKeyboard(requireActivity())
                Handler(Looper.myLooper()!!).postDelayed({
                    viewModel.setProgressLoading(false)
                    findNavController().navigate(LoginFragmentDirections.actionGoToDashboard(loginPage))
                },50)
            }
        })

        /*viewModel.goToRegister.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(LoginFragmentDirections.actionGoToRegister())
            }
        })*/

    }

    override fun onPause() {
        super.onPause()
        viewModel.setProgressLoading(true)
    }

}
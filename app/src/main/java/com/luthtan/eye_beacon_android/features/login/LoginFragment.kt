package com.luthtan.eye_beacon_android.features.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.base.util.KeyboardUtil
import com.luthtan.eye_beacon_android.databinding.FragmentLoginBinding
import com.luthtan.eye_beacon_android.features.common.PERMISSION_LOCATION_FINE
import com.luthtan.eye_beacon_android.features.common.dialog.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@FragmentScoped
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        super.onInitViews()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listener = viewModel

        permissionRequestLauncher.launch(arrayOf(PERMISSION_LOCATION_FINE))
    }

    override fun onRequestResult(granted: Boolean) {
        super.onRequestResult(granted)
        if (!granted) {
            DialogHelper.showConfirmationDialogOneButtonNoTitle(
                childFragmentManager,
                getString(R.string.login_request_permission_msg),
                getString(R.string.login_request_permission_btn_title),
                actionRightBtn = { _, _ ->
                    permissionRequestLauncher.launch(arrayOf(PERMISSION_LOCATION_FINE))
                }
            )
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.goToDashboard.observe(this, {
            it.getContentIfNotHandled()?.let { loginPage ->
                KeyboardUtil.hideKeyboard(requireActivity())
                lifecycleScope.launch {
                    delay(50L)
                    viewModel.setProgressLoading(false)
                    findNavController().navigate(LoginFragmentDirections.actionGoToDashboard(loginPage))
                }
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
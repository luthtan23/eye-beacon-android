package com.luthtan.eye_beacon_android.features.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.base.util.BaseViewModel
import com.luthtan.eye_beacon_android.base.util.SingleEvents
import com.luthtan.eye_beacon_android.data.form.ErrorLoginForm
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel(), LoginListener {

    private val _goToDashboard = MutableLiveData<SingleEvents<LoginPage>>()
    val goToDashboard: LiveData<SingleEvents<LoginPage>> = _goToDashboard

    private val _goToRegister = MutableLiveData<SingleEvents<Boolean>>()
    val goToRegister: LiveData<SingleEvents<Boolean>> = _goToRegister

    private val _errorForm = MutableLiveData<ErrorLoginForm>()
    val errorForm: LiveData<ErrorLoginForm> = _errorForm

    private val _hideProgress = MutableLiveData(true)
    val hideProgress: LiveData<Boolean> = _hideProgress

    init {
        _errorForm.value = ErrorLoginForm()
    }

    override fun onClickLinkText(view: View) {
        view.setOnClickListener {
            _goToRegister.value = SingleEvents(true)
        }
    }

    fun onClickGoToDashboard(macAddress: String, localIP: String, username: String) {
        errorForm.value?.let {
            it.uuid = 0
            it.localIP = 0
            it.username = 0

            var valid = true

            if (macAddress.isEmpty()) {
                it.uuid = R.string.uuid
                valid = false
            }

            if (localIP.isEmpty()) {
                it.localIP = R.string.local_ip
                valid = false
            }

            if (username.isEmpty()) {
                it.username = R.string.username
                valid = false
            }

            _errorForm.value = it

            if (valid) {
//                val sampleHost = "https://reqbin.com/sample/post/json"
                val loginPage = LoginPage(
                    uuid = macAddress,
                    localIP = localIP,
                    username = username
                )
                _goToDashboard.value = SingleEvents(loginPage)
            }
        }
    }

    fun setProgressLoading(state: Boolean) {
        _hideProgress.value = state
    }
}
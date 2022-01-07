package com.luthtan.eye_beacon_android.features.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luthtan.eye_beacon_android.domain.entities.login.ErrorLoginForm
import com.luthtan.eye_beacon_android.base.util.SingleEvents
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import org.koin.core.KoinComponent

class LoginViewModel() : ViewModel(), LoginListener, KoinComponent {

    private val _goToDashboard = MutableLiveData<SingleEvents<LoginPage>>()
    val goToDashboard: LiveData<SingleEvents<LoginPage>> = _goToDashboard

    private val _goToRegister = MutableLiveData<SingleEvents<Boolean>>()
    val goToRegister: LiveData<SingleEvents<Boolean>> = _goToRegister

    private val _loginModel = MutableLiveData<LoginPage>()
    val loginModel: LiveData<LoginPage> = _loginModel

    private val _errorForm = MutableLiveData<ErrorLoginForm>()
    val errorForm: LiveData<ErrorLoginForm> = _errorForm

    init {
        _errorForm.value = ErrorLoginForm()
        _loginModel.value = LoginPage(eyeBle = "", localIP = "", username = "")
    }

    override fun onClickLinkText(view: View) {
        view.setOnClickListener {
            _goToRegister.value = SingleEvents(true)
        }
    }

    fun onClickGoToDashboard(macAddress: String, localIP: String, username: String) {
        loginModel.value?.let { loginPage ->
            val sampleHost = "o"
            loginPage.eyeBle = macAddress
//            loginPage.localIP = localIP
            loginPage.localIP = sampleHost
            loginPage.username = username
            _loginModel.value = loginPage
        }
        _goToDashboard.value = SingleEvents(loginModel.value!!)
    }
}
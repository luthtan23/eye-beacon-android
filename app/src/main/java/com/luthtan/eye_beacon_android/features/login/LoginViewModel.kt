package com.luthtan.eye_beacon_android.features.login

import android.accounts.NetworkErrorException
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.base.util.BaseViewModel
import com.luthtan.eye_beacon_android.base.util.SingleEvents
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import com.luthtan.eye_beacon_android.domain.entities.login.ErrorLoginForm
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.interactor.SignInRoom
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import com.luthtan.eye_beacon_android.domain.subscriber.DefaultSubscriber
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInRoom: SignInRoom
) : BaseViewModel(), LoginListener {

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

    private val _signInRoomResponse = MutableLiveData<ResultState<BleModel>>()
    val signInRoomResponse: LiveData<ResultState<BleModel>> = _signInRoomResponse

    fun signInRoom() {
        viewModelScope.launch {
            try {
                val param = SignInRoom.Param()
                signInRoom.execute(object : DefaultSubscriber<BleModel>() {
                    override fun onError(error: ResultState<BleModel>) {
                        Timber.e(Throwable(error.toString()))
                        _signInRoomResponse.value = error
                    }

                    override fun onSuccess(data: ResultState<BleModel>) {
                        _signInRoomResponse.value = data
                    }

                    override fun onLoading(loading: ResultState<BleModel>) {
                        _signInRoomResponse.value = ResultState.Loading()
                    }

                },param)
            } catch (e: NetworkErrorException) {
                Timber.e(e)
                _signInRoomResponse.value = ResultState.Error(e)
            }
        }
    }
}
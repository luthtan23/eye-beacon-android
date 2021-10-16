package com.luthtan.eye_beacon_android.features.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luthtan.eye_beacon_android.utils.SingleEvents

class LoginViewModel : ViewModel(), LoginListener {

    private val _goToDashboard = MutableLiveData<SingleEvents<Boolean>>()
    val goToDashboard: LiveData<SingleEvents<Boolean>> = _goToDashboard

    private val _goToRegister = MutableLiveData<SingleEvents<Boolean>>()
    val goToRegister: LiveData<SingleEvents<Boolean>> = _goToRegister

    fun onLoginClick() {
        _goToDashboard.value = SingleEvents(true)
    }

    override fun onClickLinkText(view: View) {
        view.setOnClickListener {
            _goToRegister.value = SingleEvents(true)
        }
    }
}
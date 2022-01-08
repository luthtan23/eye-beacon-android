package com.luthtan.eye_beacon_android.features.dashboard

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.base.util.BaseViewModel
import com.luthtan.eye_beacon_android.domain.interactor.SignInRoom
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val signInRoom: SignInRoom
) : BaseViewModel() {

    private val _signInRoomResponse = MutableLiveData<ResultState<BleResponse>>()
    val signInRoomResponse: LiveData<ResultState<BleResponse>> = _signInRoomResponse

    fun signInRoom() {
        viewModelScope.launch {
            try {
                val param = SignInRoom.Param()
//                signInRoom.execute(param).collect {
//                    when(it) {
//                        is ResultState.Loading -> {
//                            _signInRoomResponse.value = ResultState.Loading()
//                        }
//                        is ResultState.Success -> {
//                            _signInRoomResponse.value = ResultState.Success(it.data?.body()!!)
//                        }
//                        is ResultState.Error -> {
//                            _signInRoomResponse.value = ResultState.Error(it.throwable)
//                        }
//                    }
//                }
            } catch (e: NetworkErrorException) {
                Timber.e(e)
                _signInRoomResponse.value = ResultState.Error(e)
            }
        }
    }
}
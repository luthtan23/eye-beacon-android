package com.luthtan.eye_beacon_android.features.dashboard

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.base.util.BaseViewModel
import com.luthtan.eye_beacon_android.base.util.SingleEvents
import com.luthtan.eye_beacon_android.domain.dtos.BleBody
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.interactor.GetHistoryList
import com.luthtan.eye_beacon_android.domain.interactor.InsertHistory
import com.luthtan.eye_beacon_android.domain.interactor.SignInRoom
import com.luthtan.eye_beacon_android.domain.subscriber.DefaultSubscriber
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.altbeacon.beacon.Beacon
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val signInRoom: SignInRoom,
    private val insertHistory: InsertHistory,
    private val getHistoryList: GetHistoryList
) : BaseViewModel(), DashboardListener {

    private val _signInRoomResponse = MutableLiveData<ResultState<BleModel>>()
    val signInRoomResponse: LiveData<ResultState<BleModel>> = _signInRoomResponse

    private val _bluetoothActivityAction = MutableLiveData<SingleEvents<Boolean>>()
    val bluetoothActivityAction: LiveData<SingleEvents<Boolean>> = _bluetoothActivityAction

    private val _loginPageData = MutableLiveData<LoginPage>()
    val loginPageData: LiveData<LoginPage> = _loginPageData

    private val _getHistoryListDao = MutableLiveData<ResultState<List<BleEntity>>>()
    val getHistoryListDao: LiveData<ResultState<List<BleEntity>>> = _getHistoryListDao

    private val _needRefreshRecycler = MutableLiveData<SingleEvents<Boolean>>()
    val needRefreshRecycler: LiveData<SingleEvents<Boolean>> = _needRefreshRecycler

    private val _storeBeacon = MutableLiveData<MutableList<Beacon>>()
    val storeBeacon: LiveData<MutableList<Beacon>> = _storeBeacon

    private val _countDown = MutableLiveData<Int>()
    val countDown: LiveData<Int> = _countDown

    private val _automaticallySignIn = MutableLiveData<SingleEvents<Boolean>>()
    val automaticallySignIn: LiveData<SingleEvents<Boolean>> = _automaticallySignIn

    private val _stopBeacon = MutableLiveData<SingleEvents<Boolean>>()
    val stopBeacon: LiveData<SingleEvents<Boolean>> = _stopBeacon

    private val _dismissDialog = MutableLiveData<SingleEvents<Boolean>>()
    val dismissDialog: LiveData<SingleEvents<Boolean>> = _dismissDialog

    private val _isActiveTextfield = MutableLiveData<Boolean>()
    val isActiveTextfield: LiveData<Boolean> = _isActiveTextfield

    private var breakCount = false

    private val stateBleAction = MutableLiveData(false)

    init {
        _storeBeacon.value = mutableListOf()
        _countDown.value = INIT_COUNT_DOWN
        _isActiveTextfield.value = true
    }

    fun initData(loginPage: LoginPage) {
        _loginPageData.value = loginPage
    }

    fun signInRoom(baseUrl: String, bleBody: BleBody) {
        viewModelScope.launch {
            try {
                _signInRoomResponse.value = ResultState.Loading()
                val param = SignInRoom.Param(uuid = baseUrl, bleBody)
                signInRoom.execute(object : DefaultSubscriber<BleModel>() {
                    override fun onError(error: ResultState<BleModel>) {
                        Timber.e(Throwable(error.toString()))
                        _signInRoomResponse.value = error
                    }

                    override fun onSuccess(data: ResultState<BleModel>) {
                        _isActiveTextfield.value = !bleBody.isInside
                        _signInRoomResponse.value = data
                    }

                }, param)
            } catch (e: NetworkErrorException) {
                Timber.e(e)
                _signInRoomResponse.value = ResultState.Error(e)
            }
        }
    }

    fun insertHistory(bleEntity: BleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val param = InsertHistory.Param(bleEntity)
                insertHistory.execute(object : DefaultSubscriber<Long>() {
                    override fun onError(error: ResultState<Long>) {
                        Timber.e(Throwable(error.toString()))
                    }

                    override fun onSuccess(data: ResultState<Long>) {
                        getHistoryList()
                    }
                }, param)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun setBeaconList(storeBeaconData: Collection<Beacon>) {
        storeBeacon.value?.let {
            it.clear()
            it.addAll(storeBeaconData)
            _storeBeacon.value = it
        }
    }

    fun getHistoryList() {
        viewModelScope.launch {
            try {
                _getHistoryListDao.value = ResultState.Loading()
                _needRefreshRecycler.value = SingleEvents(true)
                getHistoryList.execute(object : DefaultSubscriber<List<BleEntity>>() {
                    override fun onError(error: ResultState<List<BleEntity>>) {
                        Timber.e(Throwable(error.toString()))
                        _getHistoryListDao.value = error
                        _needRefreshRecycler.value = SingleEvents(true)
                    }

                    override fun onSuccess(data: ResultState<List<BleEntity>>) {
                        _getHistoryListDao.value = data
                        _needRefreshRecycler.value = SingleEvents(true)
                    }
                }, null)
            } catch (e: Exception) {
                Timber.e(e)
                _needRefreshRecycler.value = SingleEvents(true)
            }
        }
    }

    fun setCountDown() {
        viewModelScope.launch {
            for (i in INIT_COUNT_DOWN downTo 0) {
                _countDown.value = i
                delay(1000L)
                if (breakCount) {
                    setBreakCount(false)
                    break
                }
                if (i == 0) {
                    onRightDialogClicked()
                }
            }
        }
    }

    private fun setBreakCount(state: Boolean) {
        breakCount = state
    }

    fun setStopBeaconAction() {
        stateBleAction.value = true
        stateBleAction()
    }

    private fun stateBleAction() {
        loginPageData.value.let {
            _loginPageData.value = it
        }
        _bluetoothActivityAction.value = SingleEvents(stateBleAction.value!!)
    }

    override fun onLeftDialogClicked() {
        _stopBeacon.value = SingleEvents(true)
        _dismissDialog.value = SingleEvents(true)
    }

    override fun onRightDialogClicked() {
        setBreakCount(true)
        _automaticallySignIn.value = SingleEvents(true)
        _dismissDialog.value = SingleEvents(true)
    }

    companion object {
        private const val INIT_COUNT_DOWN = 60
    }
}
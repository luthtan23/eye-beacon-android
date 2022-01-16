package com.luthtan.eye_beacon_android.features.dashboard

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.MyApplication
import com.luthtan.eye_beacon_android.R
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
import kotlinx.coroutines.launch
import org.altbeacon.beacon.Beacon
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val myApplication: MyApplication,
    private val signInRoom: SignInRoom,
    private val insertHistory: InsertHistory,
    private val getHistoryList: GetHistoryList
) : BaseViewModel(), DashboardListener {

    private val _signInRoomResponse = MutableLiveData<ResultState<BleModel>>()
    val signInRoomResponse: LiveData<ResultState<BleModel>> = _signInRoomResponse

    private val _bluetoothActivityAction = MutableLiveData<SingleEvents<Boolean>>()
    val bluetoothActivityAction: LiveData<SingleEvents<Boolean>> = _bluetoothActivityAction

    private val _bleActionTitle = MutableLiveData<String>()
    val bleActionTitle: LiveData<String> = _bleActionTitle

    private val _loginPageData = MutableLiveData<LoginPage>()
    val loginPageData: LiveData<LoginPage> = _loginPageData

    private val _getHistoryListDao = MutableLiveData<ResultState<List<BleEntity>>>()
    val getHistoryListDao: LiveData<ResultState<List<BleEntity>>> = _getHistoryListDao

    private val _storeBeacon = MutableLiveData<MutableList<Beacon>>()
    val storeBeacon: LiveData<MutableList<Beacon>> = _storeBeacon

    private val stateBleAction = MutableLiveData(false)

    init {
        _bleActionTitle.value = myApplication.getString(R.string.dashboard_stop_bluetooth_activity)
        _storeBeacon.value = mutableListOf()
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
                        _signInRoomResponse.value = data
                    }

                },param)
            } catch (e: NetworkErrorException) {
                Timber.e(e)
                _signInRoomResponse.value = ResultState.Error(e)
            }
        }
    }

    fun insertHistory(bleEntity: BleEntity) {
        viewModelScope.launch {
            try {
                val param = InsertHistory.Param(bleEntity)
                insertHistory.execute(object : DefaultSubscriber<Long>() {
                    override fun onError(error: ResultState<Long>) {
                        Timber.e(Throwable(error.toString()))
                    }

                    override fun onSuccess(data: ResultState<Long>) {

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
                getHistoryList.execute(object : DefaultSubscriber<List<BleEntity>>() {
                    override fun onError(error: ResultState<List<BleEntity>>) {
                        Timber.e(Throwable(error.toString()))
                        _getHistoryListDao.value = error
                    }

                    override fun onSuccess(data: ResultState<List<BleEntity>>) {
                        _getHistoryListDao.value = data
                    }
                }, null)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun onBluetoothActivityClick() {
        loginPageData.value.let {
            _loginPageData.value = it
        }
        stateBleAction.value = !stateBleAction.value!!
        when(stateBleAction.value!!) {
            true -> _bleActionTitle.value = myApplication.getString(R.string.dashboard_start_bluetooth_activity)
            false -> _bleActionTitle.value = myApplication.getString(R.string.dashboard_stop_bluetooth_activity)
        }
        _bluetoothActivityAction.value = SingleEvents(stateBleAction.value!!)
    }
}
package com.luthtan.eye_beacon_android.features.dashboard

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.RemoteException
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.luthtan.eye_beacon_android.BuildConfig
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.base.util.DOMAIN_URL
import com.luthtan.eye_beacon_android.base.util.DateFormatter
import com.luthtan.eye_beacon_android.base.util.KeyboardUtil
import com.luthtan.eye_beacon_android.dashboardHistory
import com.luthtan.eye_beacon_android.dashboardHistoryEmpty
import com.luthtan.eye_beacon_android.databinding.FragmentDashboardBinding
import com.luthtan.eye_beacon_android.domain.dtos.BleBody
import com.luthtan.eye_beacon_android.domain.dtos.StateStage
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import com.luthtan.eye_beacon_android.features.common.dialog.DialogHelper
import com.luthtan.eye_beacon_android.features.dashboard.beacon.BluetoothManager
import com.luthtan.eye_beacon_android.features.dashboard.service.EddyStoneService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import org.altbeacon.beacon.*
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    BeaconConsumer,
    RangeNotifier {

    override val viewModel: DashboardViewModel by viewModels()

    override val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater)
    }

    private val args: DashboardFragmentArgs by navArgs()

    private var flagAPI = false
    private var initUuid = ""
    private var mStateStage: StateStage = StateStage.STATE_INIT
    private var indexBeacon = 0
    private var count = 0
    private var countIdle = 0

    @Inject
    lateinit var bluetoothState: BluetoothManager

    @Inject
    lateinit var beaconManager: BeaconManager

    private val dashboardDialog: DashboardDialog by lazy {
        DashboardDialog()
    }

    private var isScanning = false
        set(value) {
            field = value
            requireActivity().runOnUiThread { }
        }

    override fun onInitViews() {
        super.onInitViews()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listener = viewModel

        viewModel.initData(args.loginParams)

        with(bluetoothState) {
            enableBroadcast()
        }

        initUuid = args.loginParams.uuid

        initRecyclerView()
        viewModel.getHistoryList()

    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.signInRoomResponse.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showToast("Sending API...")
                }
                is ResultState.Success -> {
                    try {
                        flagAPI = true
                        when(it.data.status?.isInside) {
                            true -> {
                                DialogHelper.showConfirmationDialogOneButtonNoTitle(
                                    childFragmentManager,
                                    getString(R.string.dashboard_success_msg),
                                    getString(R.string.close),
                                    actionRightBtn = { dialogFragment, _ ->
                                        dialogFragment.dismiss()
                                    }
                                )
                            }
                            false -> {
                                viewModel.insertHistory(
                                    BleEntity(
                                        macAddress = initUuid,
                                        room = it.data.status.room!!,
                                        date = DateFormatter.currentDate(),
                                        timeIn = DateFormatter.currentTime()
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Timber.e(e)
                        showToast("Failed retrieve data!")
                    }
                }
                is ResultState.Error -> {
                    Timber.e(it.throwable)
                    showToast("Failed get data, please check your connection or Web Service")
                }
            }
        })

        viewModel.bluetoothActivityAction.observe(this, {
            it.getContentIfNotHandled()?.let { state ->
                KeyboardUtil.hideKeyboard(requireActivity())
                setDefaultState()
                when (state) {
                    true -> stopBluetoothActivity()
                    false -> startBluetoothActivity()
                }
            }
        })

        viewModel.loginPageData.observe(this, {
            if (it.uuid != initUuid) {
                showToast("You've been changed UUID")
                initUuid = it.uuid
            }
        })

        viewModel.automaticallySignIn.observe(this, {
            it.getContentIfNotHandled()?.let {
                setDefaultState()
            }
        })

        viewModel.stopBeacon.observe(this, {
            it.getContentIfNotHandled()?.let {
                viewModel.setStopBeaconAction()
            }
        })

        viewModel.needRefreshRecycler.observe(this, {
            it.getContentIfNotHandled()?.let {
                binding.rvDashboardHistory.requestModelBuild()
            }
        })

//        lifecycleScope.launch {
//            delay(500L)
//            dashboardDialog.show(childFragmentManager, "DashboardDialog")
//        }

        viewModel.storeBeacon.observe(this, { beacons ->
            if (beacons.isNotEmpty()) {
                beacons.find { it.bluetoothAddress.equals(initUuid) }?.let {
                    if (mStateStage == StateStage.STATE_INIT && indexBeacon != -1) {
                        viewModel.signInRoom(
                            args.loginParams.localIP.plus(DOMAIN_URL),
                            BleBody(name = args.loginParams.username, true)
                        )
                        mStateStage = StateStage.STATE_IN
                    } else if (mStateStage == StateStage.STATE_OUT && flagAPI) {
                        viewModel.signInRoom(
                            args.loginParams.localIP.plus(DOMAIN_URL),
                            BleBody(name = args.loginParams.username, false)
                        )
                        mStateStage = StateStage.STATE_IDLE
                    } else if (mStateStage == StateStage.STATE_IDLE) {  // for identify user still inside area after sign out
                        countIdle++
                        if (countIdle == COUNT_IDLE) {
                            dashboardDialog.show(childFragmentManager, "DashboardDialog")
                        }
                    }
                }
                // for identify user was in room
                when (indexBeacon == -1) {
                    mStateStage == StateStage.STATE_IN && flagAPI -> {
                        mStateStage = StateStage.STATE_OUT
                        showToast("Change state, you are in room")
                    }
                    mStateStage == StateStage.STATE_IDLE -> {
                        mStateStage = StateStage.STATE_INIT
                        flagAPI = false
                    }
                    else -> countIdle = 0
                }
            } else {
                if (mStateStage == StateStage.STATE_IN && flagAPI) {
                    showToast("Change state, you are in room")
                    mStateStage = StateStage.STATE_OUT
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvDashboardHistory.withModels {
            viewModel.getHistoryListDao.value?.let {
                when (it) {
                    is ResultState.Loading -> {
                        showToast("Loading History")
                    }
                    is ResultState.Success -> {
                        if (it.data.isNotEmpty()) {
                            it.data.forEachIndexed { index, bleEntity ->
                                dashboardHistory {
                                    id("$index")
                                    model(bleEntity)
                                }
                            }
                        } else {
                            dashboardHistoryEmpty {
                                id("emptyId $id")
                            }
                        }
                    }
                    is ResultState.Error -> {

                    }
                }
            }
        }
    }

    private fun unbindBeaconManager() {
        if (beaconManager.isBound(this)) {
            beaconManager.backgroundMode = true
            beaconManager.removeRangeNotifier(this)
        }
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(this)
        try {
            beaconManager.startRangingBeaconsInRegion(
                Region(
                    BuildConfig.APPLICATION_ID,
                    null,
                    null,
                    null
                )
            )
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun getApplicationContext(): Context {
        return requireContext()
    }

    override fun unbindService(p0: ServiceConnection) {
        requireActivity().unbindService(p0)
    }

    override fun bindService(
        intent: Intent?,
        serviceConnection: ServiceConnection,
        i: Int
    ): Boolean {
        return requireActivity().bindService(intent, serviceConnection, i)
    }

    private fun storeBeaconsAround(beacons: Collection<Beacon>) {
        count++
        if (count == COUNT_TASK) {
            indexBeacon = beacons.indexOf(beacons.find { it.bluetoothAddress == initUuid })
            viewModel.setBeaconList(beacons)
            count = 0
        }
    }

    private fun getServiceIntent(context: Context): Intent {
        return Intent(context, EddyStoneService::class.java)
    }

    private fun startScan() {
        if (!bluetoothState.isEnabled()) {
            bluetoothState.enable()
        }

        if (!beaconManager.isBound(this)) {
            showToast("Scanning")
            beaconManager.bind(this)
            beaconManager.backgroundMode = false
            Timber.d("Switch from background mode!")
        }

        isScanning = true
    }

    private fun stopScan() {
        unbindBeaconManager()
        isScanning = false
    }

    override fun didRangeBeaconsInRegion(collection: MutableCollection<Beacon>?, p1: Region?) {
        if (isScanning) {
            if (collection != null) {
                lifecycleScope.launch {
                    storeBeaconsAround(collection)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startBluetoothActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBluetoothActivity()
    }

    private fun startBluetoothActivity() {
        try {
            startScan()
            bluetoothState.enableBroadcast()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun stopBluetoothActivity() {
        try {
            if (beaconManager.isBound(this)) {
                stopScan()
            }
            bluetoothState.disableBroadcast()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun setDefaultState() {
        mStateStage = StateStage.STATE_INIT
        flagAPI = false
    }

    companion object {
        const val COUNT_TASK = 5
        const val COUNT_IDLE = 30
    }

}
package com.luthtan.eye_beacon_android.features.dashboard

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.databinding.FragmentDashboardBinding
import com.luthtan.eye_beacon_android.features.common.PERMISSION_LOCATION_FINE
import com.luthtan.eye_beacon_android.features.dashboard.adapter.DashboardAdapter
import com.luthtan.eye_beacon_android.base.util.AlertLocationDialog
import com.luthtan.eye_beacon_android.domain.subscriber.ResultState
import com.luthtan.eye_beacon_android.features.dashboard.service.EddyStoneService
import com.luthtan.simplebleproject.data.repository.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import org.altbeacon.beacon.*
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(), BeaconConsumer,
    RangeNotifier {

    override val viewModel: DashboardViewModel by viewModels()

    override val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater)
    }

    private val dashboardAdapter by lazy {
        DashboardAdapter()
    }

    val args: DashboardFragmentArgs by navArgs()

    private var isInside = false
    private var flagAPI = false

    @Inject lateinit var bluetoothState: BluetoothManager
    @Inject lateinit var beaconManager: BeaconManager

    private var isScanning = false
        set(value) {
            field = value
            requireActivity().runOnUiThread { }
        }

    override fun onInitViews() {
        super.onInitViews()

        binding.rvDashboardHistory.adapter = dashboardAdapter
        binding.tvDashboardUsername.text = args.loginParams.username
        binding.etDashboardUuid.setText(args.loginParams.eyeBle)

    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.signInRoom()

        viewModel.signInRoomResponse.observe(this, {
            when(it) {
                is ResultState.Loading -> {
                    showToast("Loading")
                }
                is ResultState.Success -> {
                    showToast(it.data.success.toString())
                }
                is ResultState.Error -> {
                    Timber.e(it.throwable)
                }
            }
        })

    }

    private fun unbindBeaconManager() {
        if (beaconManager.isBound(this) == true) {
            beaconManager.backgroundMode = true
            beaconManager.removeRangeNotifier(this)
        }
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(this)
        try {
            beaconManager.startRangingBeaconsInRegion(
                Region(
                    "com.luthtan.eye_beacon_android",
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

    override fun bindService(intent: Intent?, serviceConnection: ServiceConnection, i: Int): Boolean {
        return requireActivity().bindService(intent, serviceConnection, i)
    }

    private fun storeBeaconsAround(beacons: Collection<Beacon>) {
        if (beacons.isNotEmpty()) {
            beacons.forEach { beacon ->
                showToast(beacon.toString())
                isInside = beacon.bluetoothAddress == args.loginParams.eyeBle
                if (isInside) {
                    if (!flagAPI) {
                        requireActivity().startService(getServiceIntent(requireContext()))
                        binding.imgDashboardNotFound.visibility = View.GONE
                        binding.tvDashboardNotFoundDescription.visibility = View.GONE
//                        viewModel.setParams(args.loginParams)
//                        showToast("INI KEDETEK SAMA")
                    }
                    flagAPI = true
                } else {
                    flagAPI = false
                }
            }
        }
    }

    private fun getServiceIntent(context: Context) : Intent {
        return Intent(context, EddyStoneService::class.java)
    }

    override fun onRequestResult(granted: Boolean) {
        super.onRequestResult(granted)
        if (!granted) {
            AlertLocationDialog.showRequestPermission(requireContext()) {
                permissionRequestLauncher.launch(arrayOf(PERMISSION_LOCATION_FINE))
            }
        }
    }

    private fun startScan() {
        if (!bluetoothState.isEnabled() || beaconManager == null) {
            bluetoothState.enable()
        }

        if (beaconManager.isBound(this) != true) {
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

    enum class BluetoothState{
        STATE_OFF,
        STATE_TURNING_OFF,
        STATE_ON,
        STATE_TURNING_ON,
    }

    companion object{
        private val TAG = "DashboardFragment"
    }

    override fun didRangeBeaconsInRegion(collection: MutableCollection<Beacon>?, p1: Region?) {
        if (isScanning) {
            if (collection != null) {
                storeBeaconsAround(collection)
            }
        }
    }

    override fun onInitPause() {
        super.onInitPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (beaconManager.isBound(this) == true) {
            stopScan()
        }
    }

    override fun onResume() {
        super.onResume()
        startScan()
    }


}
package com.luthtan.eye_beacon_android.features.dashboard.beacon

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import com.luthtan.eye_beacon_android.features.dashboard.DashboardFragment
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothManager @Inject constructor(
    private val adapter: BluetoothAdapter?,
    private val context: Context
) {

    private val subject: BehaviorProcessor<DashboardFragment.BluetoothState> =
        BehaviorProcessor.createDefault(
            getStateFromAdapterState(
                adapter?.state ?: BluetoothAdapter.STATE_OFF
            )
        )

    companion object {
        val TIME_BLUETOOTH_CHECKED: Long = TimeUnit.MILLISECONDS.convert(3, TimeUnit.SECONDS)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, intent: Intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED == intent.action) {
                val state = getStateFromAdapterState(
                    intent.getIntExtra(
                        BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR
                    )
                )
                if (state == DashboardFragment.BluetoothState.STATE_TURNING_OFF) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        adapter?.enable()
                    }, TIME_BLUETOOTH_CHECKED)
                }
            }
        }
    }

    fun getStateFromAdapterState(state: Int): DashboardFragment.BluetoothState {
        return when (state) {
            BluetoothAdapter.STATE_OFF -> DashboardFragment.BluetoothState.STATE_OFF
            BluetoothAdapter.STATE_TURNING_OFF -> DashboardFragment.BluetoothState.STATE_TURNING_OFF
            BluetoothAdapter.STATE_ON -> DashboardFragment.BluetoothState.STATE_ON
            BluetoothAdapter.STATE_TURNING_ON -> DashboardFragment.BluetoothState.STATE_TURNING_ON
            else -> DashboardFragment.BluetoothState.STATE_OFF
        }
    }

    fun disable() = adapter?.disable()

    fun enable() = adapter?.enable()

    fun asFlowable(): Flowable<DashboardFragment.BluetoothState> {
        return subject
    }

    fun isEnabled() = adapter?.isEnabled == true

    fun toggle() {
        if (isEnabled()) {
            disable()
        } else {
            enable()
        }
    }

    fun enableBroadcast() {
        try {
            context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun disableBroadcast() {
        try {
            context.unregisterReceiver(receiver)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
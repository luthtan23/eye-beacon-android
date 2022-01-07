package com.luthtan.eye_beacon_android.features.dashboard

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothManager @Inject constructor(private val adapter: BluetoothAdapter?, context: Context){

    private val subject: BehaviorProcessor<DashboardFragment.BluetoothState> =
            BehaviorProcessor.createDefault(getStaeFromAdapterState(adapter?.state ?: BluetoothAdapter.STATE_OFF))

    companion object{
        val TIME_BLUETOOTH_CHECKED: Long = TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS)
    }

    init {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                if (BluetoothAdapter.ACTION_STATE_CHANGED == intent.action) {
                    val state = getStaeFromAdapterState(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR))
                    if (state == DashboardFragment.BluetoothState.STATE_TURNING_OFF) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            adapter?.enable()
                        }, TIME_BLUETOOTH_CHECKED)
                    }
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }

    fun getStaeFromAdapterState(state: Int) : DashboardFragment.BluetoothState {
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

    /*fun asFlowable(): Flowable<DashboardFragment.BluetoothState> {
        return subject
    }*/

    fun isEnabled() = adapter?.isEnabled == true

    fun toggle() {
        if (isEnabled()) {
            disable()
        } else {
            enable()
        }
    }
}
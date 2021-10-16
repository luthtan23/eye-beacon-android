package com.luthtan.eye_beacon_android.di

import android.bluetooth.BluetoothAdapter
import com.luthtan.eye_beacon_android.features.dashboard.BluetoothManager
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.koin.dsl.module

const val IBEACON_LAYOUT = "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"
const val ALTBEACON_LAYOUT = BeaconParser.ALTBEACON_LAYOUT
const val EDDYSTONE_UID_LAYOUT = BeaconParser.EDDYSTONE_UID_LAYOUT
const val EDDYSTONE_URL_LAYOUT = BeaconParser.EDDYSTONE_URL_LAYOUT
const val EDDYSTONE_TLM_LAYOUT = BeaconParser.EDDYSTONE_TLM_LAYOUT

val bluetoothModule = module {

    single {
        BluetoothAdapter.getDefaultAdapter()
    }

    single {
        BeaconManager.getInstanceForApplication(get()).apply {
            beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_UID_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_URL_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_TLM_LAYOUT))
        }
    }

    single {
        BluetoothManager(get(), get())
    }
}
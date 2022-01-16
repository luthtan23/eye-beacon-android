package com.luthtan.eye_beacon_android.di

import android.bluetooth.BluetoothAdapter
import com.luthtan.eye_beacon_android.MyApplication
import com.luthtan.eye_beacon_android.base.util.IBEACON_LAYOUT
import com.luthtan.eye_beacon_android.features.dashboard.beacon.BluetoothManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconParser.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeaconModule {
    @Singleton
    @Provides
    fun provideBeaconManagerHelper(myApplication: MyApplication): BeaconManager {
        return BeaconManager.getInstanceForApplication(myApplication).apply {
            beaconParsers.add(BeaconParser().setBeaconLayout(ALTBEACON_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_UID_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_URL_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(EDDYSTONE_TLM_LAYOUT))
            beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_LAYOUT))
        }
    }

    @Singleton
    @Provides
    fun provideBluetoothManager(
        bluetoothAdapter: BluetoothAdapter,
        myApplication: MyApplication
    ): BluetoothManager {
        return BluetoothManager(bluetoothAdapter, myApplication)
    }

    @Singleton
    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter {
        return BluetoothAdapter.getDefaultAdapter()
    }
}
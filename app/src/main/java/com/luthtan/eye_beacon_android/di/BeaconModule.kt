package com.luthtan.eye_beacon_android.di

import android.bluetooth.BluetoothAdapter
import com.luthtan.eye_beacon_android.MyApplication
import com.luthtan.eye_beacon_android.features.dashboard.beacon.BluetoothManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.altbeacon.beacon.BeaconManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeaconModule {
    @Singleton
    @Provides
    fun provideBeaconManagerHelper(myApplication: MyApplication): BeaconManager {
        return BeaconManager.getInstanceForApplication(myApplication)
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
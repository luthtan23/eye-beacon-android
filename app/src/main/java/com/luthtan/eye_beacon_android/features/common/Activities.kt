package com.luthtan.eye_beacon_android.features.common

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun AppCompatActivity.isPermissionGranted(permissionName: String) : Boolean {
    return ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED
}

fun AppCompatActivity.reqPermission(permissionName: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(permissionName), requestCode)
}
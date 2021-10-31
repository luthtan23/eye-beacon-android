package com.luthtan.eye_beacon_android.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.navigation.NavDeepLinkBuilder
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.activity.MainActivity
import com.luthtan.eye_beacon_android.features.dashboard.DashboardFragment

class EddyStoneService: Service() {

    companion object{
        const val BLE_NOTIFICATION_CHANNEL_ID = "bleChl"
        const val FOREGROUND_NOTIFICATION_ID = 3
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        goForeground("DETEKSI", "TEST")

    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(true)

    }

    private fun goForeground(status: String, message: String) {
        val notificationIntent = Intent(this, DashboardFragment::class.java)
        val pendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph_main)
            .setDestination(R.id.dashboardFragment)
            .createPendingIntent()
        val nBuilder = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val bleNotificationChannel = NotificationChannel(
                    BLE_NOTIFICATION_CHANNEL_ID, "BLE",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                nManager.createNotificationChannel(bleNotificationChannel)
                Notification.Builder(this,
                    BLE_NOTIFICATION_CHANNEL_ID
                )
            }
            else -> Notification.Builder(this)
        }

        val notification = nBuilder.setContentTitle(status)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
    }
}
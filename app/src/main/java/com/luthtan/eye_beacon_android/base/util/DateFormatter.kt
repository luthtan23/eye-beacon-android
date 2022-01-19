package com.luthtan.eye_beacon_android.base.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    @SuppressLint("SimpleDateFormat")
    fun currentDate(): String {
        val formatter = SimpleDateFormat("dd-MMMM-yy")
        return formatter.format(getCurrentDateTime())
    }

    @SuppressLint("SimpleDateFormat")
    fun currentTime(): String {
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(getCurrentDateTime()) + " WIB"
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}
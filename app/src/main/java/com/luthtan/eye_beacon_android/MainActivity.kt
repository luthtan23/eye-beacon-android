package com.luthtan.eye_beacon_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Eyebeaconandroid)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
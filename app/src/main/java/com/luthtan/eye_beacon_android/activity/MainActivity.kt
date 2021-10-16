package com.luthtan.eye_beacon_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
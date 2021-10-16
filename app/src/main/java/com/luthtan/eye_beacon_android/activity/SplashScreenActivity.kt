package com.luthtan.eye_beacon_android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luthtan.eye_beacon_android.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Intent(this, MainActivity::class.java).also { startActivity(it) }
        finish()
    }
}
package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class MainActivitysplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MainActivityWalktrough0::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}
package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivitywalktrough2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitywalktrough2)
        var donebtn=findViewById<Button>(R.id.buttondone)
        donebtn.setOnClickListener{
            val  intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
}
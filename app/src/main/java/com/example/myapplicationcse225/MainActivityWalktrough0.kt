package com.example.myapplicationcse225

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivityWalktrough0 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_walktrough0)
        var getstarted=findViewById<Button>(R.id.getstartedbtn)
        var skip=findViewById<Button>(R.id.buttonskip)
        getstarted.setOnClickListener{
            val  intent=Intent(this,MainActivityWalkTrough1::class.java)
            startActivity(intent)
        }
        skip.setOnClickListener{
            val  intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
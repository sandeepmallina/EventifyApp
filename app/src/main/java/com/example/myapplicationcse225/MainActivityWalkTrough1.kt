package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivityWalkTrough1 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_walk_trough1)
        var skipbtn=findViewById<Button>(R.id.skipbtn);
        var nextbtn=findViewById<Button>(R.id.buttonnext)
        nextbtn.setOnClickListener{
            val  intent=Intent(this,MainActivitywalktrough2::class.java)
            startActivity(intent)
        }
        skipbtn.setOnClickListener{
            val  intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



    }
}
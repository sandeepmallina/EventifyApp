package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivityrating : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activityrating)
        var rb1=findViewById<RatingBar>(R.id.ratingBar3)
        var rb2=findViewById<RatingBar>(R.id.ratingBar4)
        var tv1=findViewById<EditText>(R.id.feedbacktext)
        var btn=findViewById<Button>(R.id.button2)


            btn.setOnClickListener {
                if(rb1.rating.isNaN() || rb2.rating.isNaN() || tv1.text.isEmpty()){

                    FancyToast.makeText(this, "Fill All Fields", FancyToast.LENGTH_SHORT, FancyToast.ERROR, R.drawable.ic_confused, false).show();
                }
                else {
                    FancyToast.makeText(this, "Thank You For Your FeedBack", FancyToast.LENGTH_SHORT, FancyToast.WARNING, R.drawable.ic_smile, false).show();
                    Handler(Looper.getMainLooper()).postDelayed({
                        val i = Intent(this, MainActivityDisplayEvents::class.java)
                        startActivity(i)
                        finish()
                    }, 3000)
                }
            }

    }
}
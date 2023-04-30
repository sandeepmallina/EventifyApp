package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var singn_btn=findViewById<Button>(R.id.signup)
        val loginButton = findViewById<Button>(R.id.login)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.pwd)
        db=DBHelper(this)

//        var tt=findViewById<TextView>(R.id.text3)
        singn_btn.setOnClickListener {
            val intent = Intent(this, MainActivitysignup::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
//            val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
//            val storedName=sharedPreferences.getString("Name","")
//            val storedEmail = sharedPreferences.getString("Email", "")
//            val storedPassword = sharedPreferences.getString("Password", "")
//            if ((enteredData == storedEmail || enteredData == storedName) && enteredPassword == storedPassword  && (enteredData.isNotEmpty() && enteredPassword.isNotEmpty())) {
//                // Credentials are correct
//                val intent = Intent(this, MainActivityEventDisplay::class.java)
//                startActivity(intent)
//            } else {
//                // Credentials are incorrect
//                Toast.makeText(this, "Enter The correct Credentials", Toast.LENGTH_SHORT).show()
//            }
            val enteredUserName = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()
            if((enteredUserName.isNotEmpty() && enteredPassword.isNotEmpty())){
                val checkuser=db.checkuserpass(enteredUserName,enteredPassword)
                if(checkuser){
                    FancyToast.makeText(this, "Login Successful", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, R.drawable.ic_smile, false).show();
                    val intent = Intent(this, MainActivityDisplayEvents::class.java)
                startActivity(intent)
                }
                else
                {
                    FancyToast.makeText(this, "Login failed due to wrong credentials", FancyToast.LENGTH_SHORT, FancyToast.ERROR, R.drawable.ic_confused, false).show();
                }
            }
            else{
                FancyToast.makeText(this, "Fill all the details", FancyToast.LENGTH_SHORT, FancyToast.WARNING, R.drawable.ic_angry, false).show();
            }

        }




    }
}
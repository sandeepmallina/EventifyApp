package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivitysignup : AppCompatActivity() {
    private lateinit var db: DBHelper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitysignup)
        var submit_btn=findViewById<Button>(R.id.submitbtn)
        var name=findViewById<EditText>(R.id.nametv)
        var email=findViewById<EditText>(R.id.emailtv)
        var pwd=findViewById<EditText>(R.id.passwordtv)
        var cpwd=findViewById<EditText>(R.id.conformpwdtv)
        var upload=findViewById<ImageView>(R.id.uploadimage)
        db=DBHelper(this)




        var SELECT_IMAGE_REQUEST_CODE=200;
        upload.setOnClickListener{

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)


        }




        submit_btn.setOnClickListener {

            if (name.text.toString().isEmpty() ||
                email.text.toString().isEmpty() ||
                pwd.text.toString().isEmpty() ||
                cpwd.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Handle form submission
                val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("Name", name.text.toString().trim())
                editor.putString("Email", email.text.toString().trim())
                editor.apply()
                val Uname=name.text.toString().trim();
//                val Email=email.text.toString().trim()
                val Password=pwd.text.toString().trim()
                val cPassword=cpwd.text.toString().trim()
                val savedata=db.insertdata(Uname,Password)
                if(Password.equals(cPassword)) {
                    if (savedata)
                    {
                        FancyToast.makeText(this, "Register successful", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, R.drawable.ic_smile, false).show();

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        FancyToast.makeText(this, "User Exits", FancyToast.LENGTH_SHORT, FancyToast.ERROR, R.drawable.ic_baseline_person_24, false).show();
                        
                    }
                }
                else{
                    FancyToast.makeText(this, "Password Not Match", FancyToast.LENGTH_SHORT, FancyToast.ERROR, R.drawable.ic_confused, false).show();
                }
            }

        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val SELECT_IMAGE_REQUEST_CODE=200
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data // get the URI of the selected image
            // Save the image URI to shared preferences
            val sharedPref = getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString("selected_image_uri", selectedImageUri.toString())
                apply()
            }
        }
    }
}
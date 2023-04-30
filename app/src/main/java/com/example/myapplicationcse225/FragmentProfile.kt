package com.example.myapplicationcse225

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class FragmentProfile : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       var view=inflater.inflate(R.layout.fragment_profile, container, false)
        var Name=view.findViewById<TextView>(R.id.textView11)
        var Email=view.findViewById<TextView>(R.id.textView12)
        var imgV=view.findViewById<ImageView>(R.id.profile_image)
        val sharedPreferences = context?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val storedName=sharedPreferences?.getString("Name","")
            val storedEmail = sharedPreferences?.getString("Email", "")
        Name.text=storedName
        Email.text=storedEmail
        val sharedPref = context?.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
        val selectedImageUriString = sharedPref?.getString("selected_image_uri", null)

        if (selectedImageUriString != null) {
            val selectedImageUri = Uri.parse(selectedImageUriString)
            imgV.setImageURI(selectedImageUri)
        }

        return view
    }


}
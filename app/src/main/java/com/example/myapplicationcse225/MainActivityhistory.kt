package com.example.myapplicationcse225

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplicationcse225.databinding.ActivityMainActivityhistoryBinding

class MainActivityhistory : AppCompatActivity() {
    private lateinit var binding: ActivityMainActivityhistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainActivityhistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replace(FragmentHistory())
        binding.bottomnavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home-> replace(FragmentHistory())
                R.id.profile-> replace(FragmentProfile())
                R.id.event-> {
                    val intent = Intent(this,MainActivityDisplayEvents::class.java)
                    startActivity(intent)
                }

                else ->{

                }
            }
            true
        }
    }
    private fun replace(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout,fragment)
            commit()
        }
    }
}
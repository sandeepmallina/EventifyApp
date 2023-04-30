package com.example.myapplicationcse225

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivityDisplayEvents : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewPager1: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_display_events)
        viewPager1 = findViewById(R.id.view_pager)

        // Initialize the tabs property
        tabs = findViewById(R.id.tabs)
        val adapter=ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(Fragmentimportant(), "Coorporate")
        adapter.addFragment(Fragmentpublic(), "Public")
        adapter.addFragment(Fragmentprivate(), "Private")
        viewPager1.adapter = adapter
        tabs.setupWithViewPager(viewPager1)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView :NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_login -> {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_home -> {
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.nav_history -> {
                    val intent = Intent(this,MainActivityhistory::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_feedback->{
                    val intent = Intent(this,MainActivityrating::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }






    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
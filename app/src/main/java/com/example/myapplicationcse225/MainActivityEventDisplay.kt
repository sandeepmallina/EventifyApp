package com.example.myapplicationcse225
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.Calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.media.metrics.Event
import android.os.Build
import android.view.Gravity

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView

class MainActivityEventDisplay : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var add: ImageView
    private lateinit var dialog: AlertDialog
    private lateinit var layout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_eventdisplay)

        add = findViewById(R.id.add)
        layout = findViewById(R.id.container)

        buildDialog()

        add.setOnClickListener {
            dialog.show()
        }
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
                R.id.nav_feedback->{
                    val intent = Intent(this,MainActivityrating::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun buildDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dailog, null)
        var Year: Int? = null
        var Month: Int? = null
        var DayOfMonth: Int? = null
        var Hour:Int?=null
        var Minute:Int?=null
        var ampm:String?=null;
        var type:String?=null;
        val myCalendar= Calendar.getInstance()
        val date=view.findViewById<ImageView>(R.id.datepicker)
        val time=view.findViewById<ImageView>(R.id.timepiceker)
        val EventName=view.findViewById<EditText>(R.id.editTextText)
        val description=view.findViewById<EditText>(R.id.editTextTextPassword)
        var radiogrp=view.findViewById<RadioGroup>(R.id.radioGroup)
        val dataPicker= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            Year = year
            Month = month
            DayOfMonth = dayOfMonth
        }
        date.setOnClickListener{
            DatePickerDialog(this,dataPicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        time.setOnClickListener{
            val currentTime=Calendar.getInstance()
            val startHour=currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute=currentTime.get(Calendar.MINUTE)
            val amPm = if (currentTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
            TimePickerDialog(this, { view, hourofDay, minute->
                Hour=hourofDay;
                Minute=minute;
                ampm=amPm;

            },startHour,startMinute,false).show()
        }



        builder.setView(view)
        val title = TextView(this)
        title.text = "Event Details"
        title.gravity = Gravity.CENTER
        title.textSize = 18f
        title.setTextColor(Color.BLACK)
        title.setPadding(10, 10, 10, 10)

        builder.setCustomTitle(title)
            .setPositiveButton("Save") { dialog, _ ->

                val eventName = EventName?.text.toString()
                val descriptionText = description?.text.toString()
                val important=view.findViewById<RadioButton>(R.id.radioButton)
                if(important.isChecked){
                    type=important.text.toString();
                }
                val meeting=view.findViewById<RadioButton>(R.id.radioButton4)
                if(meeting.isChecked){
                    type=meeting.text.toString();
                }
                val personal=view.findViewById<RadioButton>(R.id.radioButton2)
                if(personal.isChecked){
                    type=personal.text.toString();
                }
                if(Hour==null||Minute==null|| DayOfMonth==null || Month==null || Year==null || type.isNullOrEmpty()){

                    Toast.makeText(this, "select all fields", Toast.LENGTH_SHORT).show()
                }
                else {
                    val sharedPreferences = getSharedPreferences("Event", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("EName", eventName)
                    editor.apply()
                    addCard(
                        eventName,
                        descriptionText,
                        "$Hour:$Minute $ampm",
                        "$DayOfMonth-${Month?.plus(1)}-$Year",
                        type.toString()
                    )
                    dialog.dismiss()
                }




                // Get the time selected by the user
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, Hour ?: 0)
                selectedTime.set(Calendar.MINUTE, Minute ?: 0)
                selectedTime.set(Calendar.SECOND, 0)
                selectedTime.set(Calendar.MILLISECOND, 0)

// Get a reference to the AlarmManager system service
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

// Create a PendingIntent for the notification
                val intent = Intent(this, MyNotificationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

// Schedule the notification to be sent at the selected time
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    selectedTime.timeInMillis,
                    pendingIntent
                )




            }




            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.round_background)





    }

    @SuppressLint("MissingInflatedId")
    private fun addCard(event: String, description: String, time: String,date:String,type:String) {
        val view = LayoutInflater.from(this).inflate(R.layout.card, null)

        val eventView = view.findViewById<TextView>(R.id.event)
        val descriptionView  = view.findViewById<TextView>(R.id.description)
        val timeView  = view.findViewById<TextView>(R.id.time_given)
        var dateView=view.findViewById<TextView>(R.id.date_given)

        val delete = view.findViewById<Button>(R.id.delete)
        var color=Color.parseColor("#F9F9F9")
        if(type=="Important"){
             color = Color.parseColor("#D21312")
        }
        else {
            if (type == "Personal") {
                color = Color.parseColor("#F97B22")
            }
            else {
                if (type == "Meeting") {
                    color = Color.parseColor("#4F200D")
                }
            }
        }



        eventView.text = event
        eventView.setTextColor(color)
        descriptionView.text=description
        timeView.text=time
        dateView.text=date



        delete.setOnClickListener {
            layout.removeView(view)
        }
        layout.addView(view)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    class MyNotificationReceiver : BroadcastReceiver() {


        override fun onReceive(context: Context, intent: Intent) {
            val sharedPreferences = context.getSharedPreferences("Event", Context.MODE_PRIVATE)
            val Event = sharedPreferences.getString("EName", null)

            // Create notification
            val channelId = "my_channel_id"
            val intent = Intent(context, MainActivityEventDisplay::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Event Remainder")
                .setContentText("$Event")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            // Show notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create notification channel
                val channel =
                    NotificationChannel(
                        channelId,
                        "My App Notifications",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notificationBuilder.build())
        }
    }


}

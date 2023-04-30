package com.example.myapplicationcse225

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import com.shashank.sony.fancytoastlib.FancyToast
import java.util.Calendar


class Fragmentprivate : Fragment() {
    private lateinit var add: ImageView
    private lateinit var dialog: AlertDialog
    private lateinit var layout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_private, container, false)
        add = view.findViewById(R.id.addevent002)
        layout = view.findViewById(R.id.container002)
        buildDialog()
        loadEvents()
        add.setOnClickListener {
            dialog.show()
        }
        return view

        // Inflate the layout for this fragment

    }
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun buildDialog() {
        val builder = AlertDialog.Builder(requireContext())
        var view = requireActivity().layoutInflater.inflate(R.layout.dailog, null)
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
        val dataPicker= DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            Year = year
            Month = month
            DayOfMonth = dayOfMonth
        }
        date.setOnClickListener{
            DatePickerDialog(requireContext(),dataPicker,myCalendar.get(Calendar.YEAR),myCalendar.get(
                Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        time.setOnClickListener{
            val currentTime= Calendar.getInstance()
            val startHour=currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute=currentTime.get(Calendar.MINUTE)
            val amPm = if (currentTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
            TimePickerDialog(requireContext(), { _, hourofDay, minute->
                Hour=hourofDay;
                Minute=minute;
                ampm=amPm;

            },startHour,startMinute,false).show()
        }



        builder.setView(view)
        val title = TextView(requireContext())
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

                    FancyToast.makeText(requireActivity(),"Fill all fields",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show()
                }
                else {
                    val sharedPreferences = context?.getSharedPreferences("Event", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putString("EName", eventName)
                    editor?.apply()
                    addCard(
                        eventName,
                        descriptionText,
                        "$Hour:$Minute $ampm",
                        "$DayOfMonth-${Month?.plus(1)}-$Year",
                        type.toString()

                    )
                    saveEvent(eventName,
                        descriptionText,
                        "$Hour:$Minute $ampm",
                        "$DayOfMonth-${Month?.plus(1)}-$Year",
                        type.toString()
                        )

                    FancyToast.makeText(requireActivity(), "Event successfully added ", FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS , R.drawable.ic_event, false).show();

                    dialog.dismiss()
                }




                // Get the time selected by the user
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, Hour ?: 0)
                selectedTime.set(Calendar.MINUTE, Minute ?: 0)
                selectedTime.set(Calendar.SECOND, 0)
                selectedTime.set(Calendar.MILLISECOND, 0)
                selectedTime.set(Calendar.YEAR, Year ?: 0)
                selectedTime.set(Calendar.MONTH, Month ?: 0)
                selectedTime.set(Calendar.DAY_OF_MONTH, DayOfMonth ?: 0)

// Get a reference to the AlarmManager system service
                val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(requireActivity(), MyNotificationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
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
    fun saveEvent(eventName: String, descriptionText: String,time: String,Date:String,type: String) {
        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val eventCount = sharedPref?.getInt("eventCount", 0)

        val editor = sharedPref?.edit()
        editor?.putString("event${eventCount}_eventName", eventName)
        editor?.putString("event${eventCount}_descriptionText", descriptionText)
        editor?.putString("event${eventCount}_data",Date )
        editor?.putString("event${eventCount}_time", time)
        editor?.putString("event${eventCount}_type", type)


        if (eventCount != null) {
            editor?.putInt("eventCount", eventCount + 1)
        }
        editor?.apply()
    }
    fun loadEvents() {
        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val eventCount = sharedPref?.getInt("eventCount", 0)

        for (i in 0 until eventCount!!) {
            val eventName = sharedPref?.getString("event${i}_eventName", "")
            val descriptionText = sharedPref.getString("event${i}_descriptionText", "")
            val time = sharedPref.getString("event${i}_time", "")
            val date=sharedPref.getString("event${i}_data","" )
            val type=sharedPref.getString("event${i}_type","" )
            if (date != null) {
                if (time != null) {
                    if (descriptionText != null) {
                        if (eventName != null) {
                            if (type != null) {
                                addCard(eventName,descriptionText,time,date,type)
                            }
                        }
                    }
                }
            }
        }
    }
    private fun addCard(event: String, description: String, time: String,date:String,type:String) {
        val view = requireActivity().layoutInflater.inflate(R.layout.card, null)

        val eventView = view.findViewById<TextView>(R.id.event)
        val descriptionView  = view.findViewById<TextView>(R.id.description)
        val timeView  = view.findViewById<TextView>(R.id.time_given)
        var dateView=view.findViewById<TextView>(R.id.date_given)

        val delete = view.findViewById<Button>(R.id.delete)
        var color= Color.parseColor("#070A52")
        if(type=="Important"){
            color = Color.parseColor("#D21312")
        }
        else {
            if (type == "Chill") {
                color = Color.parseColor("#F97B22")
            }
            else {
                if (type == "Mandatory") {
                    color = Color.parseColor("#00005C")
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
            FancyToast.makeText(requireActivity(), "Event successfully added ", FancyToast.LENGTH_SHORT,FancyToast.ERROR , R.drawable.ic_delete, false).show();

        }
        layout.addView(view)
    }

    class MyNotificationReceiver : BroadcastReceiver() {


        override fun onReceive(context: Context, intent: Intent) {
            val sharedPreferences = context.getSharedPreferences("Event", Context.MODE_PRIVATE)
            val Event = sharedPreferences.getString("EName", null)

            // Create notification
            val channelId = "my_channel_id"
            val intent = Intent(context, MainActivityDisplayEvents::class.java)
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
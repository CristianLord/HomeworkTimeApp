package com.example.homeworktime

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoActivity : AppCompatActivity() {

    private lateinit var reminder: Reminder
    private lateinit var database: AppDatabase
    private lateinit var reminderLiveData: LiveData<Reminder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        init()
    }

    private fun init(){

        database = AppDatabase.getDatabase(this)

        val idReminder = intent.getIntExtra("id", 0)

        reminderLiveData = database.reminders().get(idReminder)
        reminderLiveData.observe(this){

            reminder = it

            findViewById<TextView>(R.id.txtTitle).text = reminder.title
            findViewById<TextView>(R.id.txtDescription).text = reminder.description
            findViewById<TextView>(R.id.txtCourse).text = reminder.course
            findViewById<ImageView>(R.id.imgColorCourse).setColorFilter(Color.parseColor(reminder.colorMatter))
            findViewById<TextView>(R.id.txtDate).text = reminder.date
            val photo = findViewById<ImageView>(R.id.imgPhoto)
            photo.setImageURI(
                PhotoController().getImageUri(this, reminder.idReminder.toLong(),photo))
        }

        //buttons
        findViewById<ImageView>(R.id.btnBack).setOnClickListener{ finish() }

        findViewById<ImageButton>(R.id.BtnEdit).setOnClickListener{ startActivity(Intent(this@InfoActivity,
            SaveActivity::class.java).putExtra("reminder", reminder))}

        findViewById<ImageButton>(R.id.BtnComplete).setOnClickListener {
            if(reminder.idReminder != null){
                CoroutineScope(Dispatchers.IO).launch {
                    reminder.status = 1
                    database.reminders().update(reminder)
                }
            }
            finish()
        }

        findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
            reminderLiveData.removeObservers(this)
            CoroutineScope(Dispatchers.IO).launch {
                database.reminders().delete(reminder)
                PhotoController().deleteImage(this@InfoActivity, reminder.idReminder.toLong())
                this@InfoActivity.finish()
            }
        }
    }
}
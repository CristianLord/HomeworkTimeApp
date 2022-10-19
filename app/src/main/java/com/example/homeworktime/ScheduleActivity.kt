package com.example.homeworktime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private var listReminders = emptyList<Reminder>()
private lateinit var recycler: RecyclerView
private lateinit var adapter:ListAdapterEventSoon
private lateinit var database: AppDatabase

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        init()
    }

    private fun init(){
       findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        recycler = findViewById(R.id.listEventsSoon)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        database = AppDatabase.getDatabase(this)
        database.reminders().getAll().observe(this) {
            database = AppDatabase.getDatabase(this)
            database.reminders().getAll().observe(this) { reminderList ->
                listReminders = reminderList
                adapter = ListAdapterEventSoon(listReminders)
                recycler.adapter = adapter
            }
        }
    }
}
package com.example.homeworktime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompleteActivity : AppCompatActivity() {

    private var listReminders = emptyList<Reminder>()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter:ListAdapter
    private lateinit var database:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)
        init()
    }

    private fun init(){
        recycler = findViewById(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        loadData()
    }

    /**
     * Gets data from database to load in the activity.
     */
    private fun loadData(){
        database = AppDatabase.getDatabase(this)
        database.reminders().getComplete().observe(this) { reminderList ->
            listReminders = reminderList
            adapter = ListAdapter(this, listReminders) { onItemSelected(it) }
            recycler.adapter = adapter
        }
    }

    /**
     * This function is starts a [InfoActivity] when an item in the Reminder List was clicked.
     *
     * @param [mReminder] Reminder data to share with [InfoActivity].
     */
    private fun onItemSelected(mReminder: Reminder){
        startActivity(Intent(this@CompleteActivity, InfoActivity::class.java).
        putExtra("id", mReminder.idReminder))
    }
}
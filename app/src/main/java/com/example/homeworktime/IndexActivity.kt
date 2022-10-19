package com.example.homeworktime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IndexActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    private var listReminders = emptyList<Reminder>()
    private lateinit var recycler:RecyclerView
    private lateinit var toolbar:Toolbar
    private lateinit var adapter:ListAdapter
    private lateinit var floatingButton:FloatingActionButton
    private lateinit var database:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        init()
    }

    private fun init(){
        recycler = findViewById(R.id.listRecycler)
        toolbar = findViewById(R.id.ToolbarMain)
        floatingButton = findViewById(R.id.floatingActionButton)
        floatingButton.setOnClickListener{ startActivity(Intent(this@IndexActivity, SaveActivity::class.java)) }
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        loadData()
        setSupportActionBar(toolbar)
    }

    private fun loadData(){
        database = AppDatabase.getDatabase(this)
        database.reminders().getAll().observe(this) { reminderList ->
            listReminders = reminderList
            adapter = ListAdapter(this, listReminders) { onItemSelected(it) }
            recycler.adapter = adapter
        }
    }

    private fun onItemSelected(mReminder: Reminder){
        startActivity(Intent(this@IndexActivity, InfoActivity::class.java).
        putExtra("id", mReminder.idReminder))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.complete -> startActivity(Intent(this, CompleteActivity::class.java))
            R.id.schedule -> startActivity(Intent(this, ScheduleActivity::class.java))
            //else -> Toast.makeText(this, "No se que apretaste pa", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchData(query)
        }
        return true
    }

    private fun searchData(query:String){
        val searchQuery = "%$query%"
        database.reminders().searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(list)
            }
        }
    }
}
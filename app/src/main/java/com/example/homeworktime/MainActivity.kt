package com.example.homeworktime

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNewActivity()
    }

    private fun startNewActivity(){
        Handler().postDelayed({
            startActivity(Intent(this, IndexActivity::class.java))
            finish()
        }, 1000)
    }
}
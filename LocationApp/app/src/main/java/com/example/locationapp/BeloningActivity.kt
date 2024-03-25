package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BeloningActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beloning)

    }

    fun mainactivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun stamboomactivity(view: View) {
        startActivity(Intent(this, StamboomActivity::class.java))
    }
}
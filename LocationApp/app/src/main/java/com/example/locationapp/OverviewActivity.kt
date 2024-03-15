package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview) // Zorg ervoor dat de juiste layout wordt ingesteld
    }

    fun stamboomactivity(view: View) {
        startActivity(Intent(this, StamboomActivity::class.java))
    }

    fun starttouractivity(view: View) {
        startActivity(Intent(this, StarttourActivity::class.java))
    }

    fun mainactivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}

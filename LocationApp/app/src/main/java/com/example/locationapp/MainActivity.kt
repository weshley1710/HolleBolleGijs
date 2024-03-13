package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // TODO:  Blijf van deze knop AF!!!! met je vieze visvingers!!!!
    fun startactivity(view: View) {
        startActivity(Intent(this, LocationActivity::class.java))
    }

    fun overviewactivity(view: View) {
        startActivity(Intent(this, OverviewActivity::class.java))
    }
}


package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StamboomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamboom) // Zorg ervoor dat de juiste layout wordt ingesteld
    }

    fun mainactivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
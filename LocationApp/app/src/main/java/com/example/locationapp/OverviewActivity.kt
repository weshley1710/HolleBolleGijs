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

    // Implementeer andere levenscyclusmethoden zoals onStart(), onResume(), enz. indien nodig
}
